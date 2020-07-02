package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dto.UserDTO;
import com.upgrad.hirewheels.entities.Users;
import com.upgrad.hirewheels.exceptions.advice.GlobalExceptionHandler;


public interface UserService{
    Users getUserDetails(String Email, String Password) throws GlobalExceptionHandler;
    Users createUser(UserDTO users) throws GlobalExceptionHandler;
    Boolean updatePassword(String email, String mobileNo, String password) throws GlobalExceptionHandler;
}