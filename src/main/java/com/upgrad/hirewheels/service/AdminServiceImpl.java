package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dao.*;
import com.upgrad.hirewheels.entities.Vehicle;
import com.upgrad.hirewheels.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    VehicleDAO vehicleDAO;

    @Override
    public Vehicle enterVehicleDetails(Vehicle vehicle) throws VehicleNumberNotUniqueException {
        boolean testVehicleNumber = vehicleDAO.existsByVehicleNumber(vehicle.getVehicleNumber());
        if (testVehicleNumber) {
            throw new VehicleNumberNotUniqueException("Vehicle Number Already Exists");
        }
        Vehicle savedvehicle = vehicleDAO.save(vehicle);
        return savedvehicle;
    }

    @Override
    public Vehicle changeAvailabilityRequest(int vehicleId, int availabilityStatus) throws VehicleNotFoundException {
        Vehicle vehicle = vehicleDAO.findById(vehicleId).orElseThrow(
                ()-> new VehicleNotFoundException("Vehicle not found for Id " + vehicleId));

        vehicle.setAvailabilityStatus(availabilityStatus);
        return vehicleDAO.save(vehicle);
    }
}
