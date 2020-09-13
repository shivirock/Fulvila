package com.prestaging.fulvila;

import com.prestaging.fulvila.model.*;
import com.prestaging.fulvila.repository.AdminRepository;
import com.prestaging.fulvila.repository.BusinessDetailRepository;
import com.prestaging.fulvila.repository.PasswordResetTokenRepository;
import com.prestaging.fulvila.repository.TeamRepository;
import com.prestaging.fulvila.util.Mail;
import com.prestaging.fulvila.util.SendMail;
import com.prestaging.fulvila.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
public class AdminDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDetailController.class);

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    BusinessDetailRepository mBusinessDetailRepository;

    @Autowired
    TeamRepository mTeamRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private ResponseModel responseModel;

    @Autowired(required = true)
    private SendMail sendMail;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Mail mail;

    @Autowired
    private Util mUtil;

    @PostMapping("/api/v1/addAdmin")
    public ResponseEntity<ResponseModel> createAdmin(@RequestBody Admin admin, HttpServletRequest request) throws URISyntaxException {

        if (admin == null) {
            responseModel.setObject(null);
            responseModel.setResponseCode(StringConstants.NULL_DATA_IN_REQUEST_CODE);
            responseModel.setResponseMessage(StringConstants.NULL_DATA_IN_REQUEST);
            LOGGER.error(request.getRemoteAddr() + " " + StringConstants.NULL_DATA_IN_REQUEST);
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            admin.setTimeStamp((mUtil.generateDateAndTime()).toString());
            admin.setLastUpdateTime(mUtil.generateDateAndTime().toString());
            LOGGER.info(admin.toString());
            Admin mAdmin = adminRepository.save(admin);
            if (mAdmin == null) {
                responseModel.setObject(null);
                responseModel.setResponseCode(StringConstants.UNABLE_TO_CREATE_ADMIN_CODE);
                responseModel.setResponseMessage(StringConstants.UNABLE_TO_CREATE_ADMIN);
                LOGGER.error(request.getRemoteAddr() + " " + StringConstants.UNABLE_TO_CREATE_ADMIN);
                return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
            } else {
                mAdmin.setAdminPassword("");
                Object object = mAdmin;
                responseModel.setObject(object);
                responseModel.setResponseCode(mAdmin.getId());
                responseModel.setResponseMessage(StringConstants.SUCCESSFULLY_CREATED_ADMIN);
                LOGGER.info(request.getRemoteAddr() + " " + StringConstants.SUCCESSFULLY_CREATED_ADMIN);
                return new ResponseEntity(responseModel, HttpStatus.OK);
            }
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            dataIntegrityViolationException.printStackTrace();
            responseModel.setObject(null);
            responseModel.setResponseCode(StringConstants.EMAIL_ALREADY_REGISTER_AS_ADMIN_CODE);
            responseModel.setResponseMessage(StringConstants.EMAIL_ALREADY_REGISTER_AS_ADMIN);
            LOGGER.error(request.getRemoteAddr() + " " + dataIntegrityViolationException.toString() + ":"+ dataIntegrityViolationException.getMessage());
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            e.printStackTrace();
            responseModel.setObject(null);
            responseModel.setResponseCode(StringConstants.ADMIN_REGISTRATION_FAILED_DUE_TO_EXCEPTION_CODE);
            responseModel.setResponseMessage(StringConstants.ADMIN_REGISTRATION_FAILED_DUE_TO_EXCEPTION);
            LOGGER.error(request.getRemoteAddr() + " " + responseModel.getResponseMessage()+ ":"+ e.getCause());
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/api/v1/adminLogin")
    public ResponseEntity<ResponseModel> adminLogin(@RequestBody LoginEntity mLoginEntity, HttpServletRequest request) {

        if (mLoginEntity.getEmail() == null || mLoginEntity.getPassword() == null) {
            responseModel.setObject(null);
            responseModel.setResponseCode(StringConstants.NULL_DATA_IN_REQUEST_CODE);
            responseModel.setResponseMessage(StringConstants.NULL_DATA_IN_REQUEST);
            LOGGER.error(request.getRemoteAddr() + " " + StringConstants.NULL_DATA_IN_REQUEST);
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }

        List<Admin> admins = adminRepository.findByEmailAndPassword(mLoginEntity.getEmail(), mLoginEntity.getPassword());
        if (admins.size() == 0) {
            responseModel.setObject(null);
            responseModel.setResponseCode(StringConstants.ADMIN_WRONG_CREDENTIAL_ERROR_CODE);
            responseModel.setResponseMessage(StringConstants.ADMIN_WRONG_CREDENTIAL_ERROR);
            LOGGER.error("Source: " + request.getRemoteAddr() + ": " + mLoginEntity.getEmail() + " " + responseModel.getResponseMessage());
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        } else {
            responseModel.setObject(admins.get(0));
            responseModel.setResponseCode(StringConstants.SUCCESSFUL_ADMIN_LOGIN_CODE);
            responseModel.setResponseMessage(StringConstants.SUCCESSFUL_ADMIN_LOGIN);
            admins.get(0).setAdminPassword("*********");
            LOGGER.info(request.getRemoteAddr() + " " + responseModel.getResponseMessage());
            return new ResponseEntity(responseModel, HttpStatus.OK);
        }
    }

    @DeleteMapping("/api/v1/deleteAdmin")
    ResponseEntity<ResponseModel> deleteAdmin(@RequestBody LoginEntity mLoginEntity, HttpServletRequest request) {
        if (mLoginEntity.getPassword() == null || mLoginEntity.getEmail() == null) {
            responseModel.setObject(null);
            responseModel.setResponseCode(StringConstants.NULL_DATA_IN_REQUEST_CODE);
            responseModel.setResponseMessage(StringConstants.NULL_DATA_IN_REQUEST);
            LOGGER.error(request.getRemoteAddr() + " " + StringConstants.NULL_DATA_IN_REQUEST);
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            setBusinessDetailsReference(mLoginEntity.getEmail());
            int result = adminRepository.deleteByEmailAndPassword(mLoginEntity.getEmail(), mLoginEntity.getPassword());
            if (result == 0) {
                responseModel.setObject(null);
                responseModel.setResponseMessage(StringConstants.ADMIN_WRONG_CREDENTIAL_ERROR);
                responseModel.setResponseCode(StringConstants.ADMIN_WRONG_CREDENTIAL_ERROR_CODE);
                LOGGER.error("Source: " + request.getRemoteAddr() + ": " + mLoginEntity.getEmail() + " " + responseModel.getResponseMessage());
                return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
            } else {
                responseModel.setObject(null);
                responseModel.setResponseMessage(StringConstants.SUCCESSFUL_ADMIN_DELETION);
                responseModel.setResponseCode(StringConstants.SUCCESSFUL_ADMIN_DELETION_CODE);
                LOGGER.info("Source: " + request.getRemoteAddr() + ": " + mLoginEntity.getEmail() + " " + responseModel.getResponseMessage());
                return new ResponseEntity(responseModel, HttpStatus.OK);
            }
        } catch (Exception e) {
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.ADMIN_DELETION_FAILED_DUE_TO_EXCEPTION);
            responseModel.setResponseCode(StringConstants.ADMIN_DELETION_FAILED_DUE_TO_EXCEPTION_CODE);
            LOGGER.error("Source: " + request.getRemoteAddr() + ": " + mLoginEntity.getEmail() + " " + responseModel.getResponseMessage()+ ":"+ e.getCause());
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    private void setBusinessDetailsReference(String email) {
        LOGGER.info("Unreferencing Business Details for: " + email);
        int adminID = -1;
        try {
            adminID = adminRepository.findByEmail(email).getId();
            LOGGER.info("Unreferencing Business Details for admin ID: " + adminID);
            int result = mBusinessDetailRepository.updateReference(adminID);
            LOGGER.info("Before Deleting Admin " + email + " unreferencing " + result + " records from Business Details");
            result = mTeamRepository.deleteByAdminId(adminID);
            LOGGER.info("Before Deleting Admin " + email + " deleting " + result + " records from Team Details");
        } catch (Exception e) {
            LOGGER.error("While Deleting Admin " + email + " " + e.getMessage());
        }
    }

    @PutMapping("/api/v1/updateAdmin/{id}")
    ResponseEntity<ResponseModel> updateAdmin(@RequestBody Admin admin, @PathVariable int id, HttpServletRequest request) {
        if (admin == null || admin.getAdminEmail() == null) {
            responseModel.setObject(null);
            responseModel.setResponseCode(StringConstants.NULL_DATA_IN_REQUEST_CODE);
            responseModel.setResponseMessage(StringConstants.NULL_DATA_IN_REQUEST);
            LOGGER.error(request.getRemoteAddr() + " " + StringConstants.NULL_DATA_IN_REQUEST);
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }

        Optional<Admin> adminOptional = adminRepository.findById(id);

        if (!adminOptional.isPresent()) {
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.ADMIN_EXTRACTION_FAILED_DUE_TO_NONEXISTENCE);
            responseModel.setResponseCode(StringConstants.ADMIN_EXTRACTION_FAILED_DUE_TO_NONEXISTENCE_CODE);
            LOGGER.error(request.getRemoteAddr() + " " + admin.getAdminEmail() + " " + StringConstants.ADMIN_EXTRACTION_FAILED_DUE_TO_NONEXISTENCE);
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        } else {
            Admin newAdmin = adminOptional.get();
            if (!newAdmin.getAdminEmail().contentEquals(admin.getAdminEmail())) {
                responseModel.setObject(null);
                responseModel.setResponseMessage(StringConstants.ADMIN_DETAILS_DOESNOT_MATCH);
                responseModel.setResponseCode(StringConstants.ADMIN_DETAILS_DOESNOT_MATCH_CODE);
                LOGGER.error(request.getRemoteAddr() + " " + admin.getAdminEmail() + " " + responseModel.getResponseMessage());
                return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
            } else {
                newAdmin.setLastUpdateTime(mUtil.generateDateAndTime().toString());
                if (!(admin.getAdminName() == null)) newAdmin.setAdminName(admin.getAdminName());
                if (!(admin.getAdminContact() == null)) newAdmin.setAdminContact(admin.getAdminContact());
                if (!(admin.getDesignation() == null)) newAdmin.setDesignation(admin.getDesignation());
                if (!(admin.getLinkdinProfileURL() == null))
                    newAdmin.setLinkdinProfileURL(admin.getLinkdinProfileURL());
                LOGGER.info("Source: " + request.getRemoteAddr() + ": " + newAdmin.toString());

                newAdmin = adminRepository.save(newAdmin);
                newAdmin.setAdminPassword("********");
                responseModel.setObject(newAdmin);
                responseModel.setResponseMessage(StringConstants.ADMIN_SUCCESSFULL_UPDATE);
                responseModel.setResponseCode(StringConstants.ADMIN_SUCCESSFULL_UPDATE_CODE);
                LOGGER.info(request.getRemoteAddr() + " " + admin.getAdminEmail() + " " + responseModel.getResponseMessage());
                return new ResponseEntity(responseModel, HttpStatus.OK);
            }
        }
    }

    @PostMapping("/api/v1/resetPassword")
    public ResponseEntity<ResponseModel> resetPassword(@RequestBody LoginEntity mLoginEntity, HttpServletRequest request) {
        if (mLoginEntity == null || mLoginEntity.getEmail() == null) {
            responseModel.setObject(null);
            responseModel.setResponseCode(StringConstants.NULL_DATA_IN_REQUEST_CODE);
            responseModel.setResponseMessage(StringConstants.NULL_DATA_IN_REQUEST);
            LOGGER.error(request.getRemoteAddr() + " " + StringConstants.NULL_DATA_IN_REQUEST);
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }

        Admin admin = adminRepository.findByEmail(mLoginEntity.getEmail());
        if (admin == null) {
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.ADMIN_EXTRACTION_FAILED_DUE_TO_NONEXISTENCE);
            responseModel.setResponseCode(StringConstants.ADMIN_EXTRACTION_FAILED_DUE_TO_NONEXISTENCE_CODE);
            LOGGER.error("Source: " + request.getRemoteAddr() + ": " + mLoginEntity.getEmail() + " " + responseModel.getResponseMessage());
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }

        if (admin.getRegistrationType().contentEquals("G") || admin.getRegistrationType().contentEquals("F ")) {
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.ADMIN_PASSWORD_CANT_BE_RESET + " because " + StringConstants.ADMIN_REGISTERED_WITH_SOCIAL_NETWORK);
            responseModel.setResponseCode(StringConstants.ADMIN_PASSWORD_CANT_BE_RESET_CODE);
            LOGGER.error("Source: " + request.getRemoteAddr() + ": " + mLoginEntity.getEmail() + " " + responseModel.getResponseMessage());
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }

        String token = mUtil.generateOTP_(6);
        PasswordResetToken mPasswordResetToken = new PasswordResetToken();
        mPasswordResetToken.setEmail(mLoginEntity.getEmail());
        mPasswordResetToken.setToken(token);
        mPasswordResetToken.setExpiryDate(new Timestamp(System.currentTimeMillis() + 120000));
        mPasswordResetToken = passwordResetTokenRepository.save(mPasswordResetToken);

        if (mPasswordResetToken == null) {
            responseModel.setObject(null);
            responseModel.setResponseCode(StringConstants.UNABLE_TO_CREATE_TOKEN_CODE);
            responseModel.setResponseMessage(StringConstants.UNABLE_TO_CREATE_TOKEN);
            LOGGER.error(request.getRemoteAddr() + " " + StringConstants.UNABLE_TO_CREATE_TOKEN);
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        } else {
            mail.setToMail(mLoginEntity.getEmail());
            mail.setMailBody("OTP: " + mUtil.generateOTP(6));
            mail.setSubject("Recover Password");
            try {
                sendMail.setMailSender(javaMailSender);
                sendMail.sendMail(mail);
                responseModel.setObject(mPasswordResetToken);
                responseModel.setResponseCode(StringConstants.TOKEN_SENT_CODE);
                responseModel.setResponseMessage(StringConstants.TOKEN_SENT);
                LOGGER.info(responseModel.getResponseMessage() + " " + mLoginEntity.getEmail());
                return new ResponseEntity(responseModel, HttpStatus.OK);
            } catch (Exception e) {
                responseModel.setObject(null);
                responseModel.setResponseCode(StringConstants.UNABLE_TO_SEND_MAIL_CODE);
                responseModel.setResponseMessage(StringConstants.UNABLE_TO_SEND_MAIL);
                LOGGER.error(responseModel.getResponseMessage() + " " + e.getMessage());
                return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
            }
        }
    }

    @PutMapping("/api/v1/recoverPassword")
    public ResponseEntity<ResponseModel> recoverPassword(@RequestBody RecoverPassword recoverPassword, HttpServletRequest request) {
        if (recoverPassword == null) {
            responseModel.setObject(null);
            responseModel.setResponseCode(StringConstants.NULL_DATA_IN_REQUEST_CODE);
            responseModel.setResponseMessage(StringConstants.NULL_DATA_IN_REQUEST);
            LOGGER.error(request.getRemoteAddr() + " " + StringConstants.NULL_DATA_IN_REQUEST);
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }

        PasswordResetToken mPasswaorResetToken = passwordResetTokenRepository.findByEmailAndToken(recoverPassword.getEmail(), recoverPassword.getToken());
        if (mPasswaorResetToken == null) {
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.INVALID_TOKEN);
            responseModel.setResponseCode(StringConstants.INVALID_TOKEN_CODE);
            LOGGER.error(request.getRemoteAddr() + " " + responseModel.getResponseMessage());
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }

        if (mUtil.isTokenValid(mPasswaorResetToken.getExpiryDate().toString())) {
            try {
                Admin mAdmin = adminRepository.findByEmail(mPasswaorResetToken.getEmail());
                Optional<Admin> adminOptional = adminRepository.findById(mAdmin.getId());
                mAdmin = adminOptional.get();
                mAdmin.setLastUpdateTime(mUtil.generateDateAndTime().toString());
                mAdmin.setAdminPassword(recoverPassword.getNewPassword());
                mAdmin = adminRepository.save(mAdmin);
                mAdmin.setAdminPassword("*************");
                responseModel.setObject(mAdmin);
                responseModel.setResponseMessage(StringConstants.ADMIN_PASSWORD_RESET_SUCCESSFULLY);
                responseModel.setResponseCode(StringConstants.ADMIN_PASSWORD_RESET_SUCCESSFULLY_CODE);
                LOGGER.info(request.getRemoteAddr() + " " + responseModel.getResponseMessage());
                return new ResponseEntity(responseModel, HttpStatus.OK);
            } catch (DataIntegrityViolationException dataIntegrityViolationException) {
                dataIntegrityViolationException.printStackTrace();
                responseModel.setObject(null);
                responseModel.setResponseCode(StringConstants.NULL_DATA_IN_REQUEST_CODE);
                responseModel.setResponseMessage(StringConstants.NULL_DATA_IN_REQUEST);
                LOGGER.error(request.getRemoteAddr() + " " + dataIntegrityViolationException.toString());
                return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.INVALID_TOKEN);
            responseModel.setResponseCode(StringConstants.INVALID_TOKEN_CODE);
            LOGGER.error(request.getRemoteAddr() + " " + StringConstants.INVALID_TOKEN);
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
