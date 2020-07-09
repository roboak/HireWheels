package com.upgrad.hirewheels.controller;

import com.upgrad.hirewheels.dto.UserDTO;
import com.upgrad.hirewheels.dto.ForgetPWDDTO;
import com.upgrad.hirewheels.dto.LoginDTO;
import com.upgrad.hirewheels.exceptions.advice.GlobalExceptionHandler;
import com.upgrad.hirewheels.responsemodel.CustomResponse;
import com.upgrad.hirewheels.responsemodel.UserDetailResponse;
import com.upgrad.hirewheels.entities.User;
import com.upgrad.hirewheels.service.UserService;
import com.upgrad.hirewheels.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class AuthenticationController{

    @Autowired
    UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    UserValidator userValidator;

    @PostMapping("/users/access-token")
    public ResponseEntity userLogin(@RequestBody LoginDTO loginDTO){
        ResponseEntity responseEntity = null;
        UserDetailResponse userDetailResponse = new UserDetailResponse();
            try {
                userValidator.validateuserLogin(loginDTO);
                User userDetail = userService.getUserDetails(loginDTO);
                userDetailResponse.setUserId(userDetail.getUserId());
                userDetailResponse.setFirstName(userDetail.getFirstName());
                userDetailResponse.setLastName(userDetail.getLastName());
                userDetailResponse.setEmail(userDetail.getEmail());
                userDetailResponse.setMobileNumber(userDetail.getMobileNo());
                userDetailResponse.setWalletMoney(userDetail.getWalletMoney());
                userDetailResponse.setRoleName(userDetail.getUserRole().getRoleName());
                userDetailResponse.setSuccessMessage("User Successfully Logged In");
                responseEntity = ResponseEntity.ok(userDetailResponse);
            } catch (GlobalExceptionHandler e){
                logger.error(e.getMessage());
            }
        return responseEntity;
    }

    @PostMapping("/users")
    public ResponseEntity userSignUp(@RequestBody UserDTO userDTO) {
        ResponseEntity responseEntity = null;
        try {
            userValidator.validateUserSignUp(userDTO);
            User functionReturn = userService.createUser(userDTO);
            if (functionReturn != null) {
                CustomResponse response = new CustomResponse(new Date(), "User Successfully Signed Up", 200);
                responseEntity = new ResponseEntity(response, HttpStatus.OK);
            }
        }
        catch (GlobalExceptionHandler e){
                logger.error(e.getMessage());
        }
        return responseEntity;
    }

    @PutMapping("/users/access-token/password")
    public ResponseEntity changePassword(@RequestBody ForgetPWDDTO forgetPWDDTO) {
        ResponseEntity responseEntity = null;
        try {
            userValidator.validateChangePassword(forgetPWDDTO);
            boolean functionReturn = userService.updatePassword(forgetPWDDTO);
            if (functionReturn == true) {
                CustomResponse response = new CustomResponse(new Date(), "Password Successfully Changed", 200);
                return new ResponseEntity(response, HttpStatus.OK);
            }
        } catch (GlobalExceptionHandler e){
                logger.error(e.getMessage());
        }
        return responseEntity;
    }
}