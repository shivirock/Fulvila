package com.prestaging.fulvila.model;

import javax.persistence.*;

@Entity
@Table(name = "fulvila_business_details")
public class BusinessDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private Admin admin;
    @Column(name = "business_name", nullable = false)
    private String businessName;
    @Column(name = "business_contacts")
    private String businessContacts;
    @Column(name = "business_email")
    private String businessEmail;
    @Column(name = "business_type", nullable = false)
    private String businessType;
    @Column(name = "logo_url")
    private String logoURL;
    @Column(name = "brochure_url")
    private String brochureURL;
    @Column(name = "website")
    private String website;
    @Column(name = "facebook_url")
    private String businessFacebook;
    @Column(name = "instagram_url")
    private String businessInstagram;
    @Column(name = "pinterest_url")
    private String businessPintesrest;
    @Column(name = "linked_in_url")
    private String businessLinkedin;
    @Column(name = "whats_app_no")
    private String businessWhatsAppNo;
    @Column(name = "plan_type", nullable = false)
    private String planType;
    @Column(name = "timestamp", nullable = false)
    private String timeStamp;
    @Column(name = "last_update_time", nullable = false)
    private String lastUpdateTime;
    @Transient
    @OneToOne(mappedBy = "businessDetails", cascade = CascadeType.REMOVE)
    private ProductDetail productDetail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessContacts() {
        return businessContacts;
    }

    public void setBusinessContacts(String businessContacts) {
        this.businessContacts = businessContacts;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public String getBrochureURL() {
        return brochureURL;
    }

    public void setBrochureURL(String brochureURL) {
        this.brochureURL = brochureURL;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBusinessFacebook() {
        return businessFacebook;
    }

    public void setBusinessFacebook(String businessFacebook) {
        this.businessFacebook = businessFacebook;
    }

    public String getBusinessInstagram() {
        return businessInstagram;
    }

    public void setBusinessInstagram(String businessInstagram) {
        this.businessInstagram = businessInstagram;
    }

    public String getBusinessPintesrest() {
        return businessPintesrest;
    }

    public void setBusinessPintesrest(String businessPintesrest) {
        this.businessPintesrest = businessPintesrest;
    }

    public String getBusinessLinkedin() {
        return businessLinkedin;
    }

    public void setBusinessLinkedin(String businessLinkedin) {
        this.businessLinkedin = businessLinkedin;
    }

    public String getBusinessWhatsAppNo() {
        return businessWhatsAppNo;
    }

    public void setBusinessWhatsAppNo(String businessWhatsAppNo) {
        this.businessWhatsAppNo = businessWhatsAppNo;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return "BusinessDetails{" +
                "id=" + id +
                ", admin=" + admin +
                ", businessName='" + businessName + '\'' +
                ", businessContacts='" + businessContacts + '\'' +
                ", businessEmail='" + businessEmail + '\'' +
                ", businessType='" + businessType + '\'' +
                ", logoURL='" + logoURL + '\'' +
                ", brochureURL='" + brochureURL + '\'' +
                ", website='" + website + '\'' +
                ", businessFacebook='" + businessFacebook + '\'' +
                ", businessInstagram='" + businessInstagram + '\'' +
                ", businessPintesrest='" + businessPintesrest + '\'' +
                ", businessLinkedin='" + businessLinkedin + '\'' +
                ", businessWhatsAppNo='" + businessWhatsAppNo + '\'' +
                ", planType='" + planType + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                '}';
    }
}
