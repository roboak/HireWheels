package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dao.UserDAO;
import com.upgrad.hirewheels.dao.UserRoleDAO;
import com.upgrad.hirewheels.dto.ForgetPWDDTO;
import com.upgrad.hirewheels.dto.LoginDTO;
import com.upgrad.hirewheels.dto.UserDTO;
import com.upgrad.hirewheels.entities.User;
import com.upgrad.hirewheels.exceptions.APIException;
import com.upgrad.hirewheels.exceptions.UserAlreadyExistsException;
import com.upgrad.hirewheels.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserRoleDAO userRoleDAO;

    private Map<String, User> refreshTokenUserMap;

    private List<String> tokenStore;

    private Map<String, String> refreshTokenAccessTokenMap;

    private Map<String, User> accessTokenUserMap;

    @PostConstruct
    public void init() {
        refreshTokenUserMap = new HashMap<>();
        tokenStore = new ArrayList<>();
        refreshTokenAccessTokenMap = new HashMap<>();
        accessTokenUserMap = new HashMap<>();
    }

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
            user.setUserRole(userRoleDAO.findByRoleId(2)); //RoleId:2 for User
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


    /**
     * All the methods required for JWT
     */

    public UserDetails loadUserDetails(String email) throws UserNotFoundException {
        //JWT: Changed Email
        User user = userDAO.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("No User Available" + email);
        }
        return  new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword() , new ArrayList<>());
    }

    public void updateAccessTokenUserMap(String accessTokenPrev, String accessTokenNew, User user) {
        if (accessTokenUserMap.containsKey(accessTokenPrev)) {
            User user1 = accessTokenUserMap.get(accessTokenPrev);
            accessTokenUserMap.remove(accessTokenPrev);
            accessTokenUserMap.put(accessTokenNew, user1);
        } else {
            accessTokenUserMap.put(accessTokenNew, user);
        }
    }

    public User getUserFromAccessToken(String accessToken) {
        return accessTokenUserMap.get(accessToken);
    }

    public void removeUserFromAccessTokenMap(String accessToken) {
        if (accessTokenUserMap.containsKey(accessToken)) {
            accessTokenUserMap.remove(accessToken);
        }
    }

    public boolean isTokenPresent(String token) {
        return tokenStore.contains(token);
    }

    public void removeTokenIfPresent(String token) {
        if (tokenStore.contains(token)) {
            tokenStore.remove(token);
        }
    }

    public void updateRefreshTokenAccessTokenMap(String refreshToken, String accessToken) {
        this.refreshTokenAccessTokenMap.put(refreshToken, accessToken);
    }

    public void removeRefreshTokenAccessTokenMap(String refreshToken) {
        if (this.refreshTokenAccessTokenMap.containsKey(refreshToken)) {
            this.refreshTokenAccessTokenMap.remove(refreshToken);
        }
    }

    public String getCurrentAccessTokenFromRefreshToken(String refreshToken) {
        return this.refreshTokenAccessTokenMap.get(refreshToken);
    }

    public void addToken(String token) {
        tokenStore.add(token);
    }

    public void removeRefreshToken(String refreshToken) {
        if (refreshTokenUserMap.containsKey(refreshToken)) {
            refreshTokenUserMap.remove(refreshToken);
        }
    }

    public User getUserfromRefreshToken(String refreshToken) {
        return refreshTokenUserMap.get(refreshToken);
    }


    public void addRefreshToken(String refreshToken, User user) {
        this.refreshTokenUserMap.put(refreshToken, user);
    }

}
