package com.upgrad.hirewheels.responsemodel;

import lombok.Data;


@Data
public class UserDetailResponse {
    int userId;
    String firstName;
    String lastName;
    String email;
    long mobileNumber;
    int walletMoney;
    String successMessage;
    String errorMessage;
    String roleName;
}
