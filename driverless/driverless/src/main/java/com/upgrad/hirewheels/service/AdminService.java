package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dto.AdminActivityDTO;
import com.upgrad.hirewheels.responsemodel.AdminRequestResponse;

import java.util.List;

public interface AdminService {
    List<AdminRequestResponse> getAllAdminRequest(int requestId);
    Boolean updateRequest(AdminActivityDTO vehicleDTO, int vehicleId);
}
