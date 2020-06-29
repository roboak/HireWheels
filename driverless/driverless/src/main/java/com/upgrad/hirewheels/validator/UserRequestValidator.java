package com.upgrad.hirewheels.validator;

import com.upgrad.hirewheels.dto.AddVehicleDTO;
import com.upgrad.hirewheels.dto.OptVehicleDTO;
import com.upgrad.hirewheels.exceptions.GlobalExceptionHandler;

public interface UserRequestValidator {
    void validateOptVehicleRequest(OptVehicleDTO vehicle, int vehicleId) throws GlobalExceptionHandler;
    void validateAddVehicleRequest(AddVehicleDTO vehicleDTO, int userId) throws GlobalExceptionHandler;
}
