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


    /**
     * This method interacts with the VehicleDAO to store vehicle's data into the database.
     * @param vehicle
     * @return
     * @throws VehicleNumberNotUniqueException
     */
    @Override
    public Vehicle enterVehicleDetails(Vehicle vehicle) throws VehicleNumberNotUniqueException {
        boolean testVehicleNumber = vehicleDAO.existsByVehicleNumber(vehicle.getVehicleNumber());
        if (testVehicleNumber) {
            throw new VehicleNumberNotUniqueException("Vehicle Number Already Exists");
        }
        Vehicle savedvehicle = vehicleDAO.save(vehicle);
        return savedvehicle;
    }

    /**
     * This method changes the availability_status field of the vehicle based on the input parameters.
     * If the availability_status =0, then the vehicle is not available for booking. Similarly,
     * if the availability_status =1, then the vehicle is availble for booking.
     * @param vehicleId
     * @param availabilityStatus
     * @return
     * @throws VehicleNotFoundException
     */
    @Override
    public Vehicle changeAvailabilityRequest(int vehicleId, int availabilityStatus) throws VehicleNotFoundException {
        Vehicle vehicle = vehicleDAO.findById(vehicleId).orElseThrow(
                ()-> new VehicleNotFoundException("Vehicle not found for Id " + vehicleId));

        vehicle.setAvailabilityStatus(availabilityStatus);
        return vehicleDAO.save(vehicle);
    }
}
