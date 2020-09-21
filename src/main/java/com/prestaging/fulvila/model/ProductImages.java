package com.prestaging.fulvila.model;

import javax.persistence.*;

@Entity
@Table(name = "fulvila_product_images")
public class ProductImages {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = "product_detail_id", referencedColumnName = "id")
    private ProductDetail productDetail;
    @Column(name="image_url")
    private String url;
    @Column(name="is_primary", nullable = false)
    private char isPrimaryImage;
    @Column(name = "is_tag_updated")
    private char isTagUpdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public char getIsPrimaryImage() {
        return isPrimaryImage;
    }

    public void setIsPrimaryImage(char isPrimaryImage) {
        this.isPrimaryImage = isPrimaryImage;
    }

    public char getIsTagUpdate() {
        return isTagUpdate;
    }

    public void setIsTagUpdate(char isTagUpdate) {
        this.isTagUpdate = isTagUpdate;
    }


}
