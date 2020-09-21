package com.prestaging.fulvila.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name ="fulvila_product_details")
public class ProductDetail {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = "business_detail_id", referencedColumnName = "id")
    private BusinessDetails businessDetails;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name="product_description")
    private String productDescription;
    @Column(name="product_category")
    private String productCategory;
    @Column(name="product_sub_category")
    private String productSubCategory;
    @ElementCollection
    private List<String> tags;
    @Transient
    @OneToOne(mappedBy = "productDetail", cascade = CascadeType.REMOVE)
    private ProductImages productImages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BusinessDetails getBusinessDetails() {
        return businessDetails;
    }

    public void setBusinessDetails(BusinessDetails businessDetails) {
        this.businessDetails = businessDetails;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductSubCategory() {
        return productSubCategory;
    }

    public void setProductSubCategory(String productSubCategory) {
        this.productSubCategory = productSubCategory;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "id=" + id +
                ", businessDetails=" + businessDetails +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", productSubCategory='" + productSubCategory + '\'' +
                ", tags=" + tags +
                '}';
    }
}
