package com.upgrad.hirewheels.dto;

import lombok.Data;

@Data
public class VehicleDTO {
     String vehicleModel;
     String vehicleNumber;
     int vehicleSubCategoryId;
     String color;
     int costPerHour;
     int fuelTypeId;
     int locationId;
     String carImageUrl;
     int cityId;
     int userId;
}
