package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dao.UserRoleDAO;
import com.upgrad.hirewheels.dto.ForgetPWDDTO;
import com.upgrad.hirewheels.dto.LoginDTO;
import com.upgrad.hirewheels.dto.UserDTO;
import com.upgrad.hirewheels.entities.User;
import com.upgrad.hirewheels.dao.UserDAO;
import com.upgrad.hirewheels.exceptions.APIException;
import com.upgrad.hirewheels.exceptions.UserAlreadyExistsException;
import com.upgrad.hirewheels.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserRoleDAO userRoleDAO;

    /**
     * Checks if the user is registered or not. If registered it returns the user details else throws an error.
     * @param loginDTO
     * @return logged in user details.
     */

    public User getUserDetails(LoginDTO loginDTO) {
            User checkUser = userDAO.findByEmail(loginDTO.getEmail());
            if (checkUser == null){
                throw new APIException("User Not Registered");
            }
            User user = userDAO.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
            if (user == null){
            throw new UserNotFoundException("Unauthorized User");
            }
            return user;
    }

    /**
     * Checks if the userDTO mobile number/email is already exists or not. If not exists, saves the userDTO detail else throws an error.
     * @param userDTO
     * @return saved userDTO details.
     */

    public User createUser(UserDTO userDTO) {
            User returnedUser = userDAO.findByEmail(userDTO.getEmail());
                if ( returnedUser != null) {
                    throw new UserAlreadyExistsException("Email Already Exists");
                }
            User returnedUser1 = userDAO.findByMobileNo(userDTO.getMobileNo());
            if (returnedUser1 != null) {
                throw new UserAlreadyExistsException("Mobile Number Already Exists");
                }
            User user = new User();
            user.setWalletMoney(10000);
            user.setUserRole(userRoleDAO.findByRoleId(2));
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setMobileNo(userDTO.getMobileNo());
            User savedUser = userDAO.save(user);
            return savedUser;
    }

    /**
     * Checks if the user is registered or not. If registered it checks the new password is not equal to the current password.
     * If the password os different, it updates the password else throws an error.
     * @param forgetPWDDTO
     * @return
     */

    public Boolean updatePassword(ForgetPWDDTO forgetPWDDTO) {
            User user = userDAO.findByEmailAndMobileNo(forgetPWDDTO.getEmail(), forgetPWDDTO.getMobileNo());
            if(user == null){
                throw new APIException("Invalid Email/Mobile Number");
            }
            String currentPassword = user.getPassword();
                if(user != null && !currentPassword.equals(forgetPWDDTO.getPassword())) {
                    user.setPassword(forgetPWDDTO.getPassword());
                    userDAO.save(user);
                    return true;
                } else {
                    throw new APIException("The new password should be different from the existing one");
                }
    }

}
