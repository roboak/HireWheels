package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dao.UserRoleDAO;
import com.upgrad.hirewheels.dto.ForgetPWDDTO;
import com.upgrad.hirewheels.dto.LoginDTO;
import com.upgrad.hirewheels.dto.UserDTO;
import com.upgrad.hirewheels.entities.Users;
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

    public Users getUserDetails(LoginDTO loginDTO) {
            Users checkUser = userDAO.findByEmail(loginDTO.getEmail());
            if (checkUser == null){
                throw new UserNotFoundException("User Unauthorized");
            }
            Users user = userDAO.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
            return user;
    }

    /**
     * Checks if the userDTO mobile number/email is already exists or not. If not exists, saves the userDTO detail else throws an error.
     * @param userDTO
     * @return saved userDTO details.
     */

    public Users createUser(UserDTO userDTO) {
            Users returnedUser = userDAO.findByEmail(userDTO.getEmail());
                if ( returnedUser != null) {
                    throw new UserAlreadyExistsException("Email Already Exists");
                }
            Users returnedUser1 = userDAO.findByMobileNo(userDTO.getMobileNo());
            if (returnedUser1 != null) {
                throw new UserAlreadyExistsException("Mobile Number Already Exists");
                }
            Users users = new Users();
            users.setWalletMoney(10000);
            users.setUserRole(userRoleDAO.findByRoleId(2));
            users.setEmail(userDTO.getEmail());
            users.setPassword(userDTO.getPassword());
            users.setFirstName(userDTO.getFirstName());
            users.setLastName(userDTO.getLastName());
            users.setMobileNo(userDTO.getMobileNo());
            Users savedUser = userDAO.save(users);
            return savedUser;
    }

    /**
     * Checks if the user is registered or not. If registered it checks the new password is not equal to the current password.
     * If the password os different, it updates the password else throws an error.
     * @param forgetPWDDTO
     * @return
     */

    public Boolean updatePassword(ForgetPWDDTO forgetPWDDTO) {
            Users user = userDAO.findByEmailAndMobileNo(forgetPWDDTO.getEmail(), forgetPWDDTO.getMobileNo());
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
