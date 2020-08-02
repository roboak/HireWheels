package com.upgrad.hirewheels.utils;

import com.upgrad.hirewheels.dao.UserDAO;
import com.upgrad.hirewheels.exceptions.APIException;
import com.upgrad.hirewheels.exceptions.BadCredentialsException;
import com.upgrad.hirewheels.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Authorisation {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserDAO userDAO;

    public void adminAuthorization(String accessToken) throws BadCredentialsException {
        checkRole("Admin", accessToken);
    }

    public void userAuthorization(String accessToken) throws BadCredentialsException {
        checkRole("User", accessToken);
    }

    void checkRole(String role, String accessToken) throws BadCredentialsException {
        String email = jwtTokenProvider.getEmail(accessToken);
        if(email == null)
            throw new APIException("Access Token Invalid");
        if(!userDAO.findByEmail(email).getUserRole().getRoleName().equalsIgnoreCase(role))
            throw new BadCredentialsException("Unauthorized. Only "+ role +" can access this API");
    }


}
