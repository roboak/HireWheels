package com.upgrad.hirewheels.dto;

import lombok.Data;

@Data
public class ForgetPWDDTO {
    String email;
    long mobileNo;
    String password;
}
