package com.upgrad.hirewheels.controller;

import com.upgrad.hirewheels.dto.LoginDTO;
import com.upgrad.hirewheels.dto.UserDTO;
import com.upgrad.hirewheels.entities.User;
import com.upgrad.hirewheels.exceptions.APIException;
import com.upgrad.hirewheels.exceptions.BadCredentialsException;
import com.upgrad.hirewheels.exceptions.UserAlreadyExistsException;
import com.upgrad.hirewheels.exceptions.UserNotFoundException;
import com.upgrad.hirewheels.responsemodel.CustomResponse;
import com.upgrad.hirewheels.security.jwt.JwtTokenProvider;
import com.upgrad.hirewheels.service.UserService;
import com.upgrad.hirewheels.utils.DTOEntityConverter;
import com.upgrad.hirewheels.utils.EntityDTOConverter;
import com.upgrad.hirewheels.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@RestController
public class AuthenticationController{

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    UserValidator userValidator;

    @Autowired
    DTOEntityConverter dtoEntityConverter;

    @Autowired
    EntityDTOConverter entityDTOConverter;

    @PostMapping("/users/access-token")
    public ResponseEntity userLogin(@RequestBody LoginDTO loginDTO) throws APIException, UserNotFoundException, BadCredentialsException {
        ResponseEntity responseEntity = null;
        userValidator.validateuserLogin(loginDTO);
        User userDetail = userService.getUserDetails(loginDTO);
        UserDTO userDTO = entityDTOConverter.convertToUserDTO(userDetail);
        String token = jwtTokenProvider.createToken(userDetail.getEmail());
        userDTO.setJwtToken(token);
        responseEntity = ResponseEntity.ok(userDTO);
        return responseEntity;
    }

    @PostMapping("/users")
    public ResponseEntity userSignUp(@RequestBody UserDTO userDTO) throws APIException, UserAlreadyExistsException {
        ResponseEntity responseEntity = null;
        userValidator.validateUserSignUp(userDTO);
        User savedUser = userService.createUser(dtoEntityConverter.convertToUserEntity(userDTO));
        if (savedUser != null) {
            CustomResponse response = new CustomResponse(new Date(), "User Successfully Signed Up", 200);
            responseEntity = new ResponseEntity(response, HttpStatus.OK);
        }
        return responseEntity;
    }


}
