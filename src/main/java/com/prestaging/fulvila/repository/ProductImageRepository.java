package com.prestaging.fulvila.repository;

import com.prestaging.fulvila.model.ProductDetail;
import com.prestaging.fulvila.model.ProductImages;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductImageRepository extends CrudRepository<ProductImages, Integer> {
    @Query(value = "SELECT * FROM fulvila_product_images WHERE is_tag_updated <> 'Y'", nativeQuery = true)
    Iterable<ProductImages> findNonTaggedList();

    @Modifying(clearAutomatically = true)
    @Query(value ="UPDATE fulvila_product_images SET is_tag_updated = 'Y' WHERE id = ?1 AND product_detail_id = ?2", nativeQuery = true)
    int updateTagUpdateFlag(int id, int productDetailId);

    @Query(value = "SELECT * from fulvila_product_images WHERE product_detail_id = ?1", nativeQuery = true)
    List<ProductImages> getProductImages(int id);
}
