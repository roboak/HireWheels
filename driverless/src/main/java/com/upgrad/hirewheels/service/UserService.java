package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dto.AddUserDTO;
import com.upgrad.hirewheels.entities.Users;
import com.upgrad.hirewheels.exceptions.GlobalExceptionHandler;


public interface UserService{
    Users getUserDetails(String Email, String Password) throws GlobalExceptionHandler;
    Users createUser(AddUserDTO users) throws GlobalExceptionHandler;
    Boolean updatePassword(String email, long mobileNo, String password) throws GlobalExceptionHandler;
}