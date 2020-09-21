package com.prestaging.fulvila.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntireProduct {
    private ProductDetail productDetail;
    private List<ProductImages> productImages;

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public List<ProductImages> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImages> productImages) {
        this.productImages = productImages;
    }

    @Override
    public String toString() {
        return "EntireProduct{" +
                "productDetail=" + productDetail +
                ", productImages=" + productImages +
                '}';
    }
}
