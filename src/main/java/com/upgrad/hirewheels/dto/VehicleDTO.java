package com.upgrad.hirewheels.dto;

import lombok.Data;

@Data
public class VehicleDTO {
     int vehicleId;
     String vehicleModel;
     String vehicleNumber;
     int vehicleSubCategoryId;
     String color;
     int fuelTypeId;
     int locationId;
     String carImageUrl;
     int availability_status;
     int pricePerDay;
}
