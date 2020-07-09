package com.upgrad.hirewheels.controller;

import com.upgrad.hirewheels.dto.ForgetPWDDTO;
import com.upgrad.hirewheels.dto.LoginDTO;
import com.upgrad.hirewheels.dto.RefreshTokenRequest;
import com.upgrad.hirewheels.dto.UserDTO;
import com.upgrad.hirewheels.entities.User;
import com.upgrad.hirewheels.exceptions.APIException;
import com.upgrad.hirewheels.exceptions.advice.GlobalExceptionHandler;
import com.upgrad.hirewheels.responsemodel.CustomResponse;
import com.upgrad.hirewheels.responsemodel.UserDetailResponse;
import com.upgrad.hirewheels.security.jwt.JwtTokenProvider;
import com.upgrad.hirewheels.service.UserService;
import com.upgrad.hirewheels.service.UserServiceImpl;
import com.upgrad.hirewheels.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController{

    @Autowired
    UserService userService;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

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
                //JWT Starts
                //JWT Changed to Email
                String refreshToken = jwtTokenProvider.createRefreshToken(userDetail.getEmail());
                String token = jwtTokenProvider.createToken(userDetail.getEmail());
                String oldToken = userDetail.getJwtToken();
                userDetail.setJwtToken(token);
                userDetail.setRefreshToken(refreshToken);
                userServiceImpl.addToken(token);
                userServiceImpl.addRefreshToken(refreshToken,userDetail);
                userServiceImpl.updateRefreshTokenAccessTokenMap(refreshToken,token);
                userServiceImpl.updateAccessTokenUserMap(oldToken,token,userDetail);
                //JWT Ends
                userDetailResponse.setUserId(userDetail.getUserId());
                userDetailResponse.setFirstName(userDetail.getFirstName());
                userDetailResponse.setLastName(userDetail.getLastName());
                userDetailResponse.setEmail(userDetail.getEmail());
                userDetailResponse.setMobileNumber(userDetail.getMobileNo());
                userDetailResponse.setWalletMoney(userDetail.getWalletMoney());
                userDetailResponse.setRoleName(userDetail.getUserRole().getRoleName());
                userDetailResponse.setJwtToken(userDetail.getJwtToken());
                userDetailResponse.setRefreshToken(userDetail.getRefreshToken());
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

    @DeleteMapping("/users/access-token")
    public ResponseEntity signOut(@RequestBody RefreshTokenRequest data) throws APIException {
        try {
            String refreshToken = data.getRefreshToken();
            String accessToken = userServiceImpl.getCurrentAccessTokenFromRefreshToken(refreshToken);
            userServiceImpl.removeUserFromAccessTokenMap(userServiceImpl.getCurrentAccessTokenFromRefreshToken(refreshToken));
            userServiceImpl.removeRefreshTokenAccessTokenMap(refreshToken);
            userServiceImpl.removeRefreshToken(refreshToken);
            userServiceImpl.removeTokenIfPresent(accessToken);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (AuthenticationException e) {
            throw new APIException("Refresh token not valid");
        }
    }

    @PostMapping("/access-token/refresh")
    public ResponseEntity refreshToken(@RequestBody RefreshTokenRequest data) throws APIException {
        try {
            String refreshToken = data.getRefreshToken();
            if (userServiceImpl.getUserfromRefreshToken(refreshToken) == null) {
                throw new APIException ("Refresh Token Not Valid");
            }
            //JWT: Changed Email
            String token = jwtTokenProvider.createToken(userServiceImpl.getUserfromRefreshToken(refreshToken).getEmail());
            userServiceImpl.updateAccessTokenUserMap(userServiceImpl.getCurrentAccessTokenFromRefreshToken(refreshToken), token,
                    null);
            userServiceImpl.removeTokenIfPresent(userServiceImpl.getCurrentAccessTokenFromRefreshToken(refreshToken));
            userServiceImpl.updateRefreshTokenAccessTokenMap(refreshToken, token);
            userServiceImpl.addToken(token);

            Map<String, String> model = new HashMap<>();
            model.put("jwtToken", token);
            return ResponseEntity.ok(model);
        } catch (AuthenticationException | APIException e) {
            throw new APIException ("Refresh Token Not Valid");
        }
    }
}
