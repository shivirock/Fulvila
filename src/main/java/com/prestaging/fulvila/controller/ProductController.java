package com.prestaging.fulvila.controller;

import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import com.prestaging.fulvila.model.*;
import com.prestaging.fulvila.repository.ProductDetailRepository;
import com.prestaging.fulvila.repository.ProductImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class ProductController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDetail.class);

    @Autowired
    private ResponseModel responseModel;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ClarifaiTest mClarifaiTest;

    @Autowired
    private List<EntireProduct> entireProducts;

    @GetMapping("/addProduct")
    public ResponseEntity<ResponseModel> addProduct(@RequestBody ProductDetail productDetail, HttpServletRequest request) {
        if(productDetail.getProductName() == null) {
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.MISSING_PRODUCT_MANDATORY_FIELD);
            responseModel.setResponseCode(StringConstants.MISSING_PRODUCT_MANDATORY_FIELD_CODE);
            LOGGER.error("Requested By: "+request.getRemoteAddr()+" - Failed Due to:"+responseModel.toString());
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            productDetail = productDetailRepository.save(productDetail);
            responseModel.setObject(productDetail);
            responseModel.setResponseMessage(StringConstants.SUCCESS_PROUDCT_INSERTION);
            responseModel.setResponseCode(StringConstants.SUCCESS_PROUDCT_INSERTION_CODE);
            LOGGER.info("Requested By: "+request.getRemoteAddr()+" - Successfull, with ID:"+productDetail.getId());
            return new ResponseEntity(responseModel, HttpStatus.OK);
        } catch (Exception e) {
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.PRODUCT_SUBMISSION_UNKNOWN_EXCEPTION);
            responseModel.setResponseCode(StringConstants.PRODUCT_SUBMISSION_UNKNOWN_EXCEPTION_CODE);
            LOGGER.error("Requested By: "+request.getRemoteAddr()+" - Failed Due to:"+responseModel.toString());
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/addImages")
    public ResponseEntity<ResponseModel> addImages(@RequestBody List<ProductImages> productImages, HttpServletRequest request) {
        if(productImages.size() == 0) {
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.MISSING_PRODUCT_IMAGE_MANDATORY_FIELD);
            responseModel.setResponseCode(StringConstants.MISSING_PRODUCT_IMAGE_MANDATORY_FIELD_CODE);
            LOGGER.error("Requested By: "+request.getRemoteAddr()+" - Failed Due to:"+responseModel.toString());
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            responseModel.setObject(productImageRepository.saveAll(productImages));
            responseModel.setResponseMessage(StringConstants.SUCCESS_PROUDCT_IMAGE_INSERTION);
            responseModel.setResponseCode(StringConstants.SUCCESS_PROUDCT_IMAGE_INSERTION_CODE);
            LOGGER.info("Requested By: "+request.getRemoteAddr()+" - Successfull");
            return new ResponseEntity(responseModel, HttpStatus.OK);

        } catch (Exception e) {
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.PRODUCT_IMAGE_SUBMISSION_UNKNOWN_EXCEPTION);
            responseModel.setResponseCode(StringConstants.PRODUCT_IMAGE_SUBMISSION_UNKNOWN_EXCEPTION_CODE);
            LOGGER.error("Requested By: "+request.getRemoteAddr()+" - Failed Due to:"+responseModel.toString());
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    //second, minute, hour, day, month, weekday CRON Pattern
    @Transactional
    //@Scheduled(initialDelay = 30000,fixedRate = 300000)
    @Scheduled(cron="0 0 0 * * ?")
    public void submitTag(){
        Iterable<ProductImages> productImages = productImageRepository.findNonTaggedList();
        LOGGER.info("No of Non Tagged Product Images: "+((Collection<?>) productImages).size());

        for (ProductImages p: productImages){
            ProductDetail productDetail = productDetailRepository.findById(p.getProductDetail().getId());
            LOGGER.info("Product Details with ID: "+p.getProductDetail().getId()+" found");

            //Appending auto generated tag via clarifai
            List<String> appendTags = productDetail.getTags();
            appendTags.addAll(findTagsViaClarifai(p.getUrl()));

            //Removing duplicate tags
            Set<String> set = new HashSet<>(appendTags);
            appendTags.clear();
            appendTags.addAll(set);

            //Setting new set of tags
            productDetail.setTags(appendTags);
            productDetail = productDetailRepository.save(productDetail);

            //Updated Product Image Flagges for which Tags have already been updated
            int result = productImageRepository.updateTagUpdateFlag(p.getId(), p.getProductDetail().getId());
            LOGGER.info(result+ " records updated in Product Images");
        }
    }

    private List<String> findTagsViaClarifai(String url){
        List<String> tags = new ArrayList<>();
        List<ClarifaiOutput<Concept>> response = new ArrayList<>();

        try {
            response = mClarifaiTest.getClient().getDefaultModels().generalModel()
                    .predict()
                    .withInputs(ClarifaiInput.forImage(url))
                    .executeSync()
                    .get();
        } catch (Exception e) {
            LOGGER.error("tags fetching failed from Clarify");
        }

        LOGGER.info("Got "+response.size()+" tags from clarify for URL \""+url+"\"");

        for(int i = 0; i<response.size(); i++){
            List<Concept> concept = response.get(i).data();
            for (Concept c:concept) {
                tags.add(c.name());
            }
        }
        return tags;
    }

    @GetMapping("/searchbytag")
    public ResponseEntity<ResponseModel> searchByTag(@RequestBody String[] tags, HttpServletRequest request) {
        List<ProductDetail> productDetails = productDetailRepository.findProductDetailsByTag(tags[0]);

        if(productDetails.size() == 0) {
            LOGGER.info("Request Source:"+request.getRemoteAddr()+" 0 products found for the tag: "+tags[0]);
            responseModel.setObject(null);
            responseModel.setResponseCode(StringConstants.NO_PRODUCT_FOUND_CODE);
            responseModel.setResponseMessage(StringConstants.NO_PRODUCT_FOUND);
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }

        EntireProduct mEntireProduct = new EntireProduct();
        entireProducts.clear();
        LOGGER.info("Request Source:"+request.getRemoteAddr()+" "+productDetails.size()+" products found for the tag: "+tags[0]);

        for (ProductDetail pd: productDetails) {
            mEntireProduct.setProductDetail(pd);
            mEntireProduct.getProductDetail().getBusinessDetails().getAdmin().setAdminPassword(null);
            mEntireProduct.setProductImages(productImageRepository.getProductImages(pd.getId()));
            LOGGER.info("Request Source:"+request.getRemoteAddr()+" "+productDetails.size()+" products found for the tag: "+tags[0]);
            for(int i = 0; i < mEntireProduct.getProductImages().size(); i++) {
                mEntireProduct.getProductImages().get(i).setProductDetail(null);
            }
            entireProducts.add(mEntireProduct);
        }
        responseModel.setResponseCode(productDetails.size());
        responseModel.setResponseMessage(StringConstants.PROUDCT_FOUND);
        responseModel.setObject(entireProducts);
        return new ResponseEntity(responseModel, HttpStatus.OK);
    }
}
