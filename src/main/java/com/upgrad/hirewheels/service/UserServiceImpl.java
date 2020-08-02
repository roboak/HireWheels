package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dao.UserDAO;
import com.upgrad.hirewheels.dto.LoginDTO;
import com.upgrad.hirewheels.entities.User;
import com.upgrad.hirewheels.exceptions.BadCredentialsException;
import com.upgrad.hirewheels.exceptions.UserAlreadyExistsException;
import com.upgrad.hirewheels.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDAO userDAO;

    /**
     * Checks if the user is registered or not.
     * If registered it returns the user details else throws an error.
     * @param loginDTO
     * @return
     * @throws BadCredentialsException
     * @throws UserNotFoundException
     */

    public User getUserDetails(LoginDTO loginDTO) throws BadCredentialsException, UserNotFoundException {
            User checkUser = userDAO.findByEmail(loginDTO.getEmail());
            if (checkUser == null){
                throw new UserNotFoundException("User Not Registered");
            }
            User user = userDAO.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
            if (user == null){
            throw new BadCredentialsException("Unauthorized User");
            }
            return user;
    }

    /**
     * Checks if the userDTO mobile number/email is already exists or not.
     * If not exists, saves the userDTO detail else throws an error.
     * @param user
     * @return
     * @throws UserAlreadyExistsException
     */

    public User createUser(User user) throws UserAlreadyExistsException {
            User returnedUser = userDAO.findByEmail(user.getEmail());
            if ( returnedUser != null) {
                    throw new UserAlreadyExistsException("Email Already Exists");
                }
            User returnedUser1 = userDAO.findByMobileNo(user.getMobileNo());
            if (returnedUser1 != null) {
                throw new UserAlreadyExistsException("Mobile Number Already Exists");
            }
            User savedUser = userDAO.save(user);
            return savedUser;
    }

    /**
     * Method required for JWT
     */

    public UserDetails loadUserDetails(String email) throws UserNotFoundException {
        //JWT: Changed Email
        User user = userDAO.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("No User Available" + email);
        }
        return  new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword() , new ArrayList<>());
    }

}
