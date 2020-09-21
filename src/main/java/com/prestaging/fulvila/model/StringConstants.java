package com.prestaging.fulvila.model;

public class StringConstants {
    //Admin Operation
    //Success Response
    public static final String SUCCESSFUL_ADMIN_LOGIN = "Admin login is successful";
    public static final int SUCCESSFUL_ADMIN_LOGIN_CODE = 1;
    public static final String SUCCESSFUL_ADMIN_DELETION = "Admin deletion is successful";
    public static final int SUCCESSFUL_ADMIN_DELETION_CODE = 2;
    public static final String ADMIN_SUCCESSFULL_UPDATE = "Admin is successfully updated";
    public static final int ADMIN_SUCCESSFULL_UPDATE_CODE = 3;
    public static final String TOKEN_SENT = "Successfully sent the token";
    public static final int TOKEN_SENT_CODE = 4;
    public static final String ADMIN_PASSWORD_RESET_SUCCESSFULLY = "Admin password is reset successfully";
    public static final int ADMIN_PASSWORD_RESET_SUCCESSFULLY_CODE = 5;

    //Error Response
    public static final String SUCCESSFULLY_CREATED_ADMIN = "Admin Registered Successfully";
    public static final String NULL_DATA_IN_REQUEST = "Request with null data";
    public static final int NULL_DATA_IN_REQUEST_CODE = -1;
    public static final String UNABLE_TO_CREATE_ADMIN = "Unable to register Admin";
    public static final int UNABLE_TO_CREATE_ADMIN_CODE = -2;
    public static final String EMAIL_ALREADY_REGISTER_AS_ADMIN = "Email is already registered as Admin or You are passing null value to non null reference";
    public static final int EMAIL_ALREADY_REGISTER_AS_ADMIN_CODE = -3;
    public static final String ADMIN_REGISTRATION_FAILED_DUE_TO_EXCEPTION = "Admin registration failed due to some unknown exception";
    public static final int ADMIN_REGISTRATION_FAILED_DUE_TO_EXCEPTION_CODE = -4;
    public static final String ADMIN_EXTRACTION_FAILED_DUE_TO_NONEXISTENCE = "Admin is not registered on Fulvila";
    public static final int ADMIN_EXTRACTION_FAILED_DUE_TO_NONEXISTENCE_CODE = -5;
    public static final String ADMIN_DELETION_FAILED_DUE_TO_EXCEPTION = "Admin deletion failed due to some unknown exception";
    public static final int ADMIN_DELETION_FAILED_DUE_TO_EXCEPTION_CODE = -6;
    public static final String ADMIN_WRONG_CREDENTIAL_ERROR = "Credential does not match";
    public static final int ADMIN_WRONG_CREDENTIAL_ERROR_CODE = -7;
    public static final String UNABLE_TO_CREATE_TOKEN = "Unable to create token";
    public static final int UNABLE_TO_CREATE_TOKEN_CODE = -8;
    public static final String UNABLE_TO_SEND_MAIL = "Failed to send mail";
    public static final int UNABLE_TO_SEND_MAIL_CODE = -9;
    public static final String INVALID_TOKEN = "Token is invalid";
    public static final int INVALID_TOKEN_CODE = -10;
    public static final String ADMIN_PASSWORD_CANT_BE_RESET = "Admin password is reset successfully";
    public static final int ADMIN_PASSWORD_CANT_BE_RESET_CODE = -11;
    public static final String ADMIN_REGISTERED_WITH_SOCIAL_NETWORK = "Admin is registered with either Google or Facebook";
    public static final int ADMIN_DETAILS_DOESNOT_MATCH_CODE = -12;
    public static final String ADMIN_DETAILS_DOESNOT_MATCH = "Admin ID and Email does not match";

    //Business Response
    //Success Response
    public static final String SUCCESSFULLY_CREATED_BUSINESS = "Business Registered Successfully";
    public static final String SUCCESSFULLY_UPDATED_BUSINESS = "Business is successfully updated";
    public static final int SUCCESSFULLY_UPDATED_BUSINESS_CODE = 6;
    public static final String SUCCESSFULLY_DELETED_BUSINESS = "Business is successfully deleted";
    public static final int SUCCESSFULLY_DELETED_BUSINESS_CODE = 7;
    //Error Response
    public static final int BUSINESS_REGISTRATION_FAILED_DUE_TO_EXCEPTION_CODE = -13;
    public static final String BUSINESS_REGISTRATION_FAILED_DUE_TO_EXCEPTION = "Business Details are not registered on Fulvila due to unknown error";
    public static final int UNABLE_TO_CREATE_BUSINESS_CODE = -14;
    public static final String UNABLE_TO_CREATE_BUSINESS = "Unable to register business details due to null value to non null reference";
    public static final int UNABLE_TO_FIND_BUSINESS_CODE = -15;
    public static final String UNABLE_TO_FIND_BUSINESS = "Unable to find business with the given code";
    public static final int UNABLE_TO_UPDATE_BUSINESS_CODE = -16;
    public static final String UNABLE_TO_UPDATE_BUSINESS = "Unable to update business with the given code";
    public static final int BUSINESS_ID_DOES_NOT_EXIST_CODE = -17;
    public static final String BUSINESS_ID_DOES_NOT_EXIST = "Given business ID does not exist in our database or already been deleted";

    //Common Error Response
    public static final String UNKNOWN_ERROR = "Unknown Error" ;
    public static final int UNKNOWN_ERROR_CODE = -17 ;

    //Team Response
    //Success Response
    public static final String SUCCESSFULLY_CREATED_TEAM_MEMBER = "Team Member Registered Successfully";
    public static final String SUCCESSFULLY_UPDATED_TEAM_MEMBER = "Team Member is successfully updated";
    public static final int SUCCESSFULLY_UPDATED_TEAM_MEMBER_CODE = 8;
    public static final String SUCCESSFULLY_DELETED_TEAM_MEMBER = "Team Member is successfully deleted";
    public static final int SUCCESSFULLY_DELETED_TEAM_MEMBER_CODE = 9;
    //Error Response
    public static final String TEAM_MEMBER_CREATION_FAILED = "Team Member Registeration is failed";
    public static final int TEAM_MEMBER_CREATION_FAILED_CODE = -18;
    public static final String TEAM_MEMBER_CREATION_FAILED_DUE_TO_CONSTRAINT = "Team Member Registeration is failed due to Team member already exist or wrong admin ID is passed";
    public static final int TEAM_MEMBER_CREATION_FAILED_DUE_TO_CONSTRAINT_CODE = -19;
    public static final String UNABLE_TO_FIND_TEAM_MEMBER = "Team Member does not Exist";
    public static final int UNABLE_TO_FIND_TEAM_MEMBER_CODE = -20;
    public static final String UNABLE_TO_UPDATED_TEAM_MEMBER = "Team Member updation is failed";
    public static final int UNABLE_TO_UPDATED_TEAM_MEMBER_CODE = -21;
    public static final int TEAM_ID_DOES_NOT_EXIST_CODE = -22;
    public static final String TEAM_ID_DOES_NOT_EXIST = "Given Team ID does not exist in our database or already been deleted";

    //Product Detail
    //Success Message
    public static final String SUCCESS_PROUDCT_INSERTION ="Successfully Inserted the product details";
    public static final int SUCCESS_PROUDCT_INSERTION_CODE = 10;
    public static final String SUCCESS_PROUDCT_IMAGE_INSERTION ="Successfully Inserted the product details";
    public static final int SUCCESS_PROUDCT_IMAGE_INSERTION_CODE = 11;
    public static final String PROUDCT_FOUND ="Products found";
    //Error Message
    public static final String MISSING_PRODUCT_MANDATORY_FIELD ="Mandatory field from product details are missing";
    public static final int MISSING_PRODUCT_MANDATORY_FIELD_CODE = -23;
    public static final String PRODUCT_SUBMISSION_UNKNOWN_EXCEPTION ="Unknown exception found while inserting product details";
    public static final int PRODUCT_SUBMISSION_UNKNOWN_EXCEPTION_CODE = -24;
    public static final String MISSING_PRODUCT_IMAGE_MANDATORY_FIELD ="Mandatory field from product images are missing";
    public static final int MISSING_PRODUCT_IMAGE_MANDATORY_FIELD_CODE = -25;
    public static final String PRODUCT_IMAGE_SUBMISSION_UNKNOWN_EXCEPTION ="Unknown exception found while inserting product images";
    public static final int PRODUCT_IMAGE_SUBMISSION_UNKNOWN_EXCEPTION_CODE = -26;
    public static final String NO_PRODUCT_FOUND ="No Product Found";
    public static final int NO_PRODUCT_FOUND_CODE = -27;
}
