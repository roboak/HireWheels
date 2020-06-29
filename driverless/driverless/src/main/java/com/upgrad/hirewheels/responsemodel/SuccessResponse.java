package com.upgrad.hirewheels.responsemodel;

import lombok.Data;

import java.util.Date;

@Data
public class SuccessResponse {
    private Date timestamp;
    private String message;
    private String details;
    private int statusCode;

    public SuccessResponse(Date timestamp, String message, String details, int statusCode) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.statusCode = statusCode;
    }
}
