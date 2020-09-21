package com.prestaging.fulvila.repository;

import com.prestaging.fulvila.model.ProductDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductDetailRepository extends CrudRepository<ProductDetail,Integer> {
    @Query("SELECT p FROM  ProductDetail p JOIN FETCH p.tags WHERE p.id = (:id)")
    public ProductDetail findById(int id);

    @Query(value = "select fpd.id, fpd.product_name, fpd.product_description, fpd.product_category, " +
            "fpd.product_sub_category,fpd.business_detail_id from  fulvila_product_details fpd " +
            "WHERE fpd.id IN (SELECT product_detail_id from product_detail_tags WHERE tags = ?1)",nativeQuery = true)
    public List<ProductDetail> findProductDetailsByTag(String tag);
}
