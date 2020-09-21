package com.prestaging.fulvila.controller;

import com.prestaging.fulvila.model.*;
import com.prestaging.fulvila.repository.AdminRepository;
import com.prestaging.fulvila.repository.BusinessDetailRepository;
import com.prestaging.fulvila.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
public class BusinessDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessDetails.class);

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    BusinessDetailRepository mBusinessDetailRepository;

    @Autowired
    ResponseModel responseModel;

    @Autowired
    Util mUtil;

    @PostMapping("/api/v1/addbusiness")
    public ResponseEntity<ResponseModel> addBusiness(@RequestBody BusinessDetails mBusinessDetails, HttpServletRequest request) {
        if(mBusinessDetails.getAdmin() == null || mBusinessDetails.getBusinessName() == null || mBusinessDetails.getBusinessType() == null || mBusinessDetails.getPlanType() == null) {
            responseModel.setObject(null);
            responseModel.setResponseCode(StringConstants.NULL_DATA_IN_REQUEST_CODE);
            responseModel.setResponseMessage(StringConstants.NULL_DATA_IN_REQUEST);
            LOGGER.error(request.getRemoteAddr()+" "+StringConstants.NULL_DATA_IN_REQUEST);
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            mBusinessDetails.setTimeStamp((mUtil.generateDateAndTime()).toString());
            mBusinessDetails.setLastUpdateTime(mUtil.generateDateAndTime().toString());
            LOGGER.info(mBusinessDetails.toString());
            mBusinessDetails.setBusinessType(changeAsInternalBusinessTye(mBusinessDetails.getBusinessType()));
            BusinessDetails businessDetails = mBusinessDetailRepository.save(mBusinessDetails);
            if(businessDetails == null) {
                responseModel.setObject(null);
                responseModel.setResponseCode(StringConstants.UNABLE_TO_CREATE_BUSINESS_CODE);
                responseModel.setResponseMessage(StringConstants.UNABLE_TO_CREATE_BUSINESS);
                LOGGER.error(request.getRemoteAddr()+" "+StringConstants.UNABLE_TO_CREATE_BUSINESS);
                return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
            } else {
                Object object = businessDetails;
                responseModel.setObject(object);
                responseModel.setResponseCode(businessDetails.getId());
                responseModel.setResponseMessage(StringConstants.SUCCESSFULLY_CREATED_BUSINESS);
                LOGGER.info(request.getRemoteAddr()+" "+StringConstants.SUCCESSFULLY_CREATED_BUSINESS);
                return new ResponseEntity(responseModel, HttpStatus.OK);
            }
        }catch(Exception e) {
            e.printStackTrace();
            responseModel.setObject(null);
            responseModel.setResponseCode(StringConstants.BUSINESS_REGISTRATION_FAILED_DUE_TO_EXCEPTION_CODE);
            responseModel.setResponseMessage(StringConstants.BUSINESS_REGISTRATION_FAILED_DUE_TO_EXCEPTION);
            LOGGER.error(request.getRemoteAddr()+" "+e.toString());
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    private String changeAsInternalBusinessTye(String businessType) {
        String result = "";
        char[] type = {'0', '0', '0', '0'};

        if(businessType.contains("C")){
            type[0] = '1';
        }

        if(businessType.contains("B")){
            type[1] = '1';
        }

        if(businessType.contains("V")){
            type[2] = '1';
        }

        if(businessType.contains("A")){
            type[3] = '1';
        }

        return String.valueOf(type);
    }

    @PutMapping("/api/v1/updateBusiness/{id}")
    public ResponseEntity<ResponseModel> updateBusiness(@RequestBody BusinessDetails mBusinessDetails, @PathVariable int id, HttpServletRequest request){
        Optional<BusinessDetails> mOptionalBusinessDetails = mBusinessDetailRepository.findById(id);
        if(!mOptionalBusinessDetails.isPresent()) {
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.UNABLE_TO_FIND_BUSINESS);
            responseModel.setResponseCode(StringConstants.UNABLE_TO_FIND_BUSINESS_CODE);
            LOGGER.error("Requested From: "+request.getRemoteAddr()+" Failed Due to: "+responseModel.getResponseMessage());
            return new ResponseEntity<>(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }

        BusinessDetails businessDetails = mOptionalBusinessDetails.get();
        businessDetails.setLastUpdateTime(mUtil.generateDateAndTime().toString());
        if(!(mBusinessDetails.getBusinessName() == null)) businessDetails.setBusinessName(mBusinessDetails.getBusinessName());
        if(!(mBusinessDetails.getBrochureURL() == null)) businessDetails.setBrochureURL(mBusinessDetails.getBrochureURL());
        if(!(mBusinessDetails.getLogoURL() == null)) businessDetails.setLogoURL(mBusinessDetails.getLogoURL());
        if(!(mBusinessDetails.getBusinessContacts() == null)) businessDetails.setBusinessContacts(mBusinessDetails.getBusinessContacts());
        if(!(mBusinessDetails.getBusinessEmail() == null)) businessDetails.setBusinessEmail(mBusinessDetails.getBusinessEmail());
        if(!(mBusinessDetails.getBusinessFacebook() == null)) businessDetails.setBusinessFacebook(mBusinessDetails.getBusinessFacebook());
        if(!(mBusinessDetails.getBusinessInstagram() == null)) businessDetails.setBusinessInstagram(mBusinessDetails.getBusinessInstagram());
        if(!(mBusinessDetails.getBusinessLinkedin() == null)) businessDetails.setBusinessLinkedin(mBusinessDetails.getBusinessLinkedin());
        if(!(mBusinessDetails.getBusinessPintesrest() == null)) businessDetails.setBusinessPintesrest(mBusinessDetails.getBusinessPintesrest());
        if(!(mBusinessDetails.getBusinessWhatsAppNo() == null)) businessDetails.setBusinessWhatsAppNo(mBusinessDetails.getBusinessWhatsAppNo());
        if(!(mBusinessDetails.getWebsite() == null)) businessDetails.setWebsite(mBusinessDetails.getWebsite());
        if(!(mBusinessDetails.getBusinessType() == null)) businessDetails.setWebsite(changeAsInternalBusinessTye(mBusinessDetails.getWebsite()));

        businessDetails = mBusinessDetailRepository.save(businessDetails);

        if(businessDetails == null) {
            responseModel.setObject(null);
            responseModel.setResponseCode(StringConstants.UNABLE_TO_UPDATE_BUSINESS_CODE);
            responseModel.setResponseMessage(StringConstants.UNABLE_TO_UPDATE_BUSINESS);
            LOGGER.error("Requested From: "+request.getRemoteAddr()+" Failed Due to: "+responseModel.getResponseMessage());
            return new ResponseEntity<>(responseModel, HttpStatus.NOT_ACCEPTABLE);
        } else {
            responseModel.setObject(businessDetails);
            businessDetails.setAdmin(null);
            responseModel.setResponseCode(StringConstants.SUCCESSFULLY_UPDATED_BUSINESS_CODE);
            responseModel.setResponseMessage(StringConstants.SUCCESSFULLY_UPDATED_BUSINESS);
            LOGGER.error("Requested From: "+request.getRemoteAddr()+" : "+responseModel.getResponseMessage());
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }
    }
    @DeleteMapping("/api/v1/deleteBusiness/{ids}")
    public ResponseEntity<ResponseModel> deleteBusiness(@RequestBody LoginEntity mLoginEntity, @PathVariable int[] ids, HttpServletRequest request){
        List<Admin> admins = adminRepository.findByEmailAndPassword(mLoginEntity.getEmail(), mLoginEntity.getPassword());
        if(admins.size() == 0) {
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.ADMIN_WRONG_CREDENTIAL_ERROR);
            responseModel.setResponseCode(StringConstants.ADMIN_WRONG_CREDENTIAL_ERROR_CODE);
            LOGGER.error("Requested By: "+request.getRemoteAddr()+" for deletion operation of business & failed due to "+ responseModel.getResponseMessage());
            return new ResponseEntity<>(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }

        for (int id:ids) {
            try {
                int result = mBusinessDetailRepository.deleteByAdminIDAndID(id, admins.get(0).getId());
                if(result == 0) {
                    responseModel.setObject(null);
                    responseModel.setResponseMessage(StringConstants.BUSINESS_ID_DOES_NOT_EXIST+" for admin: "+mLoginEntity.getEmail());
                    responseModel.setResponseCode(StringConstants.BUSINESS_ID_DOES_NOT_EXIST_CODE);
                    LOGGER.error("Requested By: "+request.getRemoteAddr()+" for deletion operation of business with ID "+id+" failed due to "+ responseModel.getResponseMessage());
                    return new ResponseEntity<>(responseModel, HttpStatus.NOT_ACCEPTABLE);
                }
            } catch (EmptyResultDataAccessException e) {
                e.printStackTrace();
                responseModel.setObject(null);
                responseModel.setResponseMessage(StringConstants.BUSINESS_ID_DOES_NOT_EXIST+" for ID: "+id);
                responseModel.setResponseCode(StringConstants.BUSINESS_ID_DOES_NOT_EXIST_CODE);
                LOGGER.error("Requested By: "+request.getRemoteAddr()+" for deletion operation of business with ID "+id+" failed due to "+ responseModel.getResponseMessage());
                return new ResponseEntity<>(responseModel, HttpStatus.NOT_ACCEPTABLE);
            }  catch (Exception e) {
                e.printStackTrace();
                responseModel.setObject(null);
                responseModel.setResponseMessage(StringConstants.UNKNOWN_ERROR);
                responseModel.setResponseCode(StringConstants.UNKNOWN_ERROR_CODE);
                LOGGER.error("Requested By: "+request.getRemoteAddr()+" for deletion operation of business with ID "+id+" failed due to "+ responseModel.getResponseMessage()+" "+e.getCause());
                return new ResponseEntity<>(responseModel, HttpStatus.NOT_ACCEPTABLE);
            }
        }

        responseModel.setObject(null);
        responseModel.setResponseMessage(StringConstants.SUCCESSFULLY_DELETED_BUSINESS);
        responseModel.setResponseCode(StringConstants.SUCCESSFULLY_DELETED_BUSINESS_CODE);
        LOGGER.info("Requested By: "+request.getRemoteAddr()+" for deletion operation for business IDS: "+ids.toString()+". "+ responseModel.getResponseMessage());
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }
}
