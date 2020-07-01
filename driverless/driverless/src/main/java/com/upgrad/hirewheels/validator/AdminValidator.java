package com.upgrad.hirewheels.validator;

import com.upgrad.hirewheels.dto.AdminActivityDTO;
import com.upgrad.hirewheels.exceptions.GlobalExceptionHandler;

public interface AdminValidator {
    void validateApprovals(int requestId) throws GlobalExceptionHandler;
    void validateUpdateVehicleRequest(AdminActivityDTO vehicle, int vehicleId) throws GlobalExceptionHandler;
}
