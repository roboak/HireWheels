package com.upgrad.hirewheels.controller;

import com.upgrad.hirewheels.dto.UserDTO;
import com.upgrad.hirewheels.dto.ForgetPWDDTO;
import com.upgrad.hirewheels.dto.LoginDTO;
import com.upgrad.hirewheels.exceptions.APIException;
import com.upgrad.hirewheels.exceptions.GlobalExceptionHandler;
import com.upgrad.hirewheels.responsemodel.SuccessResponse;
import com.upgrad.hirewheels.responsemodel.UserDetailResponse;
import com.upgrad.hirewheels.entities.Users;
import com.upgrad.hirewheels.service.UserService;
import com.upgrad.hirewheels.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController{

    @Autowired
    UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    UserValidator userValidator;

    @PostMapping("/users/access-token")
    public ResponseEntity userLogin(@RequestBody LoginDTO user){
        ResponseEntity responseEntity = null;
        UserDetailResponse userDetailResponse = new UserDetailResponse();
            try {
                userValidator.validateuserLogin(user);
                Users userDetail = userService.getUserDetails(user.getEmail(), user.getPassword());
                if (userDetail != null) {
                    userDetailResponse.setUserId(userDetail.getUserId());
                    userDetailResponse.setFirstName(userDetail.getFirstName());
                    userDetailResponse.setLastName(userDetail.getLastName());
                    userDetailResponse.setEmail(userDetail.getEmail());
                    userDetailResponse.setMobileNumber(userDetail.getMobileNo());
                    userDetailResponse.setWalletMoney(userDetail.getWalletMoney());
                    userDetailResponse.setRoleName(userDetail.getUserRole().getRoleName());
                    userDetailResponse.setSuccessMessage("User Successfully Logged In");
                    responseEntity = ResponseEntity.ok(userDetailResponse);
                } else {
                    throw new APIException("Invalid Credentials");
                }
            } catch (GlobalExceptionHandler e){
                logger.error(e.getMessage());
            }
        return responseEntity;
    }

    @PostMapping("/users")
    public ResponseEntity userSignUp(@RequestBody UserDTO user) {
        ResponseEntity responseEntity = null;
        try {
            userValidator.validateUserSignUp(user);
            Users functionReturn = userService.createUser(user);
            if (functionReturn != null) {
                SuccessResponse response = new SuccessResponse(new Date(), "User Successfully Signed Up", 200);
                responseEntity = new ResponseEntity(response, HttpStatus.OK);
            }
        }
        catch (GlobalExceptionHandler e){
                logger.error(e.getMessage());
        }
        return responseEntity;
    }

    @PutMapping("/users/access-token/password")
    public ResponseEntity changePassword(@RequestBody ForgetPWDDTO user) {
        ResponseEntity responseEntity = null;
        try {
            userValidator.validateChangePassword(user);
            boolean functionReturn = userService.updatePassword(user.getEmail(), user.getMobileNo(), user.getPassword());
            if (functionReturn == true) {
                SuccessResponse response = new SuccessResponse(new Date(), "Password Successfully Changed", 200);
                return new ResponseEntity(response, HttpStatus.OK);
            }
        } catch (GlobalExceptionHandler e){
                logger.error(e.getMessage());
        }
        return responseEntity;
    }
}