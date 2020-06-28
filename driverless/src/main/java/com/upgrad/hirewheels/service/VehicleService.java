package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.responsemodel.VehicleDetailResponse;

import java.util.Date;
import java.util.List;

public interface VehicleService {
    List<VehicleDetailResponse> getAvailableVehicles(String categoryName, Date pickUpDate, Date dropDate);
    List<VehicleDetailResponse> getAllVehicleByUserId(int userId);
}
