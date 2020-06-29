package com.upgrad.hirewheels.dto;

import lombok.Data;

@Data
public class AddUserDTO {
    String firstName;
    String lastName;
    String email;
    String password;
    long mobileNo;
}
