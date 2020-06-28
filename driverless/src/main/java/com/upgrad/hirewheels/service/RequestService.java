package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dto.AddVehicleDTO;
import com.upgrad.hirewheels.dto.OptVehicleDTO;
import com.upgrad.hirewheels.entities.AdminRequest;
import com.upgrad.hirewheels.entities.Vehicle;


public interface RequestService {
AdminRequest userOptRequest(OptVehicleDTO vehicleDTO, int vehicleId);
    Vehicle addVehicleRequest(AddVehicleDTO vehicle, int userId);
}
