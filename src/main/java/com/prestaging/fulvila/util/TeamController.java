package com.prestaging.fulvila.util;

import com.prestaging.fulvila.model.*;
import com.prestaging.fulvila.repository.AdminRepository;
import com.prestaging.fulvila.repository.TeamRepository;
import org.hibernate.exception.ConstraintViolationException;
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
public class TeamController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    ResponseModel responseModel;

    @Autowired
    TeamRepository mTeamRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    Util mUtil;

    @PostMapping("/api/v1/addTeamMember")
    public ResponseEntity<Team> addTeamMember(@RequestBody Team team, HttpServletRequest request){
        if(team.getAdmin() == null || team.getEmail() == null || team.getName() == null) {
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.NULL_DATA_IN_REQUEST);
            responseModel.setResponseCode(StringConstants.NULL_DATA_IN_REQUEST_CODE);
            LOGGER.error("Requested By: "+request.getRemoteAddr()+" for adding team member failed due to "+responseModel.getResponseMessage());
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }

        if(team.getAdmin().getId() == 0) {
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.NULL_DATA_IN_REQUEST);
            responseModel.setResponseCode(StringConstants.NULL_DATA_IN_REQUEST_CODE);
            LOGGER.error("Requested By: "+request.getRemoteAddr()+" for adding team member failed due to "+responseModel.getResponseMessage());
            return new ResponseEntity(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }

        LOGGER.error("Requested By: "+request.getRemoteAddr()+" for adding team member with details "+ team.toString());

        try{
            team.setTimeStamp(mUtil.generateDateAndTime().toString());
            team.setLastUpdateTime(mUtil.generateDateAndTime().toString());
            team.setPermissionType(changeAsInternalPermissionType(team.getPermissionType()));
            Team mTeam = mTeamRepository.save(team);
            mTeam.setPassword("******");
            responseModel.setObject(mTeam);
            responseModel.setResponseMessage(StringConstants.SUCCESSFULLY_CREATED_TEAM_MEMBER);
            responseModel.setResponseCode(mTeam.getId());
            LOGGER.info("Requested By: "+request.getRemoteAddr()+" for adding team member successfull for member "+mTeam.getEmail());
            return new ResponseEntity(responseModel, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.TEAM_MEMBER_CREATION_FAILED);
            responseModel.setResponseCode(StringConstants.TEAM_MEMBER_CREATION_FAILED_CODE);
            LOGGER.error("Requested By: "+request.getRemoteAddr()+" for adding team member failed due to "+responseModel.getResponseMessage() +" "+e.getCause());
            return new ResponseEntity(responseModel, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.TEAM_MEMBER_CREATION_FAILED_DUE_TO_CONSTRAINT);
            responseModel.setResponseCode(StringConstants.TEAM_MEMBER_CREATION_FAILED_DUE_TO_CONSTRAINT_CODE);
            LOGGER.error("Requested By: "+request.getRemoteAddr()+" for adding team member failed due to "+responseModel.getResponseMessage() +" "+e.getCause());
            return new ResponseEntity(responseModel, HttpStatus.OK);
        }
    }

    @DeleteMapping("/api/v1/deleteTeam/{id}")
    public ResponseEntity<ResponseModel> deleteTeam(@RequestBody LoginEntity mLoginEntity, @PathVariable int id, HttpServletRequest request){
        if(mLoginEntity.getEmail() == null || mLoginEntity.getPassword() == null) {
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.NULL_DATA_IN_REQUEST);
            responseModel.setResponseCode(StringConstants.NULL_DATA_IN_REQUEST_CODE);
            LOGGER.error("Requested By: "+request.getRemoteAddr()+ " delete of team failed due to "+responseModel.getResponseMessage());
        }

        List<Admin> admins = adminRepository.findByEmailAndPassword(mLoginEntity.getEmail(), mLoginEntity.getPassword());
        if(admins.size() == 0) {
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.ADMIN_WRONG_CREDENTIAL_ERROR);
            responseModel.setResponseCode(StringConstants.ADMIN_WRONG_CREDENTIAL_ERROR_CODE);
            LOGGER.error("Requested By: "+request.getRemoteAddr()+" for deletion operation of business & failed due to "+ responseModel.getResponseMessage());
            return new ResponseEntity<>(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            int result = mTeamRepository.deleteByAdminIDAndID(id,admins.get(0).getId());
            if(result == 0) {
                responseModel.setObject(null);
                responseModel.setResponseMessage(StringConstants.TEAM_ID_DOES_NOT_EXIST+" for admin: "+mLoginEntity.getEmail());
                responseModel.setResponseCode(StringConstants.TEAM_ID_DOES_NOT_EXIST_CODE);
                LOGGER.error("Requested By: "+request.getRemoteAddr()+" for deletion operation of team with ID "+id+" failed due to "+ responseModel.getResponseMessage());
                return new ResponseEntity<>(responseModel, HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.TEAM_ID_DOES_NOT_EXIST+" for ID: "+id);
            responseModel.setResponseCode(StringConstants.TEAM_ID_DOES_NOT_EXIST_CODE);
            LOGGER.error("Requested By: "+request.getRemoteAddr()+" for deletion operation of team with ID "+id+" failed due to "+ responseModel.getResponseMessage());
            return new ResponseEntity<>(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }  catch (Exception e) {
            e.printStackTrace();
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.UNKNOWN_ERROR);
            responseModel.setResponseCode(StringConstants.UNKNOWN_ERROR_CODE);
            LOGGER.error("Requested By: "+request.getRemoteAddr()+" for deletion operation of team with ID "+id+" failed due to "+ responseModel.getResponseMessage()+" "+e.getCause());
            return new ResponseEntity<>(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }

        responseModel.setObject(null);
        responseModel.setResponseMessage(StringConstants.SUCCESSFULLY_DELETED_TEAM_MEMBER);
        responseModel.setResponseCode(StringConstants.SUCCESSFULLY_DELETED_TEAM_MEMBER_CODE);
        LOGGER.info("Requested By: "+request.getRemoteAddr()+" for deletion operation for team id: "+id+". "+ responseModel.getResponseMessage());
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    private String changeAsInternalPermissionType(String businessType) {
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

    @PutMapping("/api/v1/updateTeamMember/{id}")
    public ResponseEntity<ResponseModel> updateTeamMember(@RequestBody Team team,@PathVariable int id, HttpServletRequest request) {
        LOGGER.info("Requested from: "+request.getRemoteAddr()+" to update the info of team member "+team.toString());
        Optional<Team> mOptionalTeamDetails = mTeamRepository.findById(id);
        if(!mOptionalTeamDetails.isPresent()) {
            responseModel.setObject(null);
            responseModel.setResponseMessage(StringConstants.UNABLE_TO_FIND_TEAM_MEMBER);
            responseModel.setResponseCode(StringConstants.UNABLE_TO_FIND_TEAM_MEMBER_CODE);
            LOGGER.error("Requested From: "+request.getRemoteAddr()+" Failed Due to: "+responseModel.getResponseMessage());
            return new ResponseEntity<>(responseModel, HttpStatus.NOT_ACCEPTABLE);
        }

        Team mTeam = mOptionalTeamDetails.get();
        mTeam.setLastUpdateTime(mUtil.generateDateAndTime().toString());
        if(!(team.getName() == null)) mTeam.setName(team.getName());
        if(!(team.getDesignation() == null)) mTeam.setDesignation(team.getDesignation());
        if(!(team.getPermissionType() == null)) mTeam.setPermissionType(changeAsInternalPermissionType(team.getPermissionType()));


        mTeam = mTeamRepository.save(mTeam);

        if(mTeam == null) {
            responseModel.setObject(null);
            responseModel.setResponseCode(StringConstants.UNABLE_TO_UPDATED_TEAM_MEMBER_CODE);
            responseModel.setResponseMessage(StringConstants.UNABLE_TO_UPDATED_TEAM_MEMBER);
            LOGGER.error("Requested From: "+request.getRemoteAddr()+" Failed Due to: "+responseModel.getResponseMessage());
            return new ResponseEntity<>(responseModel, HttpStatus.NOT_ACCEPTABLE);
        } else {
            responseModel.setObject(mTeam);
            mTeam.setAdmin(null);
            responseModel.setResponseCode(StringConstants.SUCCESSFULLY_UPDATED_TEAM_MEMBER_CODE);
            responseModel.setResponseMessage(StringConstants.SUCCESSFULLY_UPDATED_TEAM_MEMBER);
            LOGGER.info("Requested From: "+request.getRemoteAddr()+" : "+responseModel.getResponseMessage());
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }
    }
}
