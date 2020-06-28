package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dto.OptVehicleDTO;
import com.upgrad.hirewheels.entities.AdminRequest;
import com.upgrad.hirewheels.responsemodel.AvailableRequestResponse;

import java.util.List;

public interface AdminService {
    List<AvailableRequestResponse> getAllAdminRequest(int requestId);
    AdminRequest updateRequest(OptVehicleDTO vehicleDTO, int vehicleId);
}
