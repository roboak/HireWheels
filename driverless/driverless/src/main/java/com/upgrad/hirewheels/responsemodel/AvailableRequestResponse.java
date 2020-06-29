package com.upgrad.hirewheels.responsemodel;

import lombok.Data;

@Data
public class AvailableRequestResponse {
    int requestId;
    String userComments;
    String adminComments;
    int requestStatusId;
    int vehicleId;
    int userId;
    int activityId;
}
