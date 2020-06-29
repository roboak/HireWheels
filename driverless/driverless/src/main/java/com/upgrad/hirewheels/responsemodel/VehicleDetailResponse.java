package com.upgrad.hirewheels.responsemodel;

import com.upgrad.hirewheels.entities.Booking;
import lombok.Data;

import java.util.List;

@Data
public class VehicleDetailResponse {
    int vehicleId;
    String vehicleModel;
    int vehicleOwner;
    String vehicleNumber;
    String color;
    String fuelType;
    int locationId;
    String locationName;
    String address;
    String cityName;
    int pincode;
    String carImageUrl;
    int costPerHour;
    int activityId;
    int requestStatusId;
}
