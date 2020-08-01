package com.upgrad.hirewheels.dto;

import lombok.Data;

@Data
public class UserDTO {

    int userId;
    String firstName;
    String lastName;
    String email;
    String password;
    String mobileNo;
    int walletMoney;
    String roleName;
    String jwtToken;
}
