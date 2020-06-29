package com.upgrad.hirewheels.validator;

import com.upgrad.hirewheels.dto.OptVehicleDTO;
import com.upgrad.hirewheels.exceptions.GlobalExceptionHandler;

public interface AdminValidator {
    void validateApprovals(int requestId) throws GlobalExceptionHandler;
    void validateAddVehicleRequest(OptVehicleDTO vehicle, int vehicleId) throws GlobalExceptionHandler;
}
