package com.upgrad.hirewheels.service;
import com.upgrad.hirewheels.entities.Vehicle;
import com.upgrad.hirewheels.exceptions.APIException;
import com.upgrad.hirewheels.exceptions.VehicleNotFoundException;
import com.upgrad.hirewheels.exceptions.VehicleNumberNotUniqueException;


public interface AdminService {

    Vehicle enterVehicleDetails(Vehicle vehicle) throws VehicleNumberNotUniqueException;
    Vehicle changeAvailabilityRequest(int vehicleId, int availabilityStatus)throws VehicleNotFoundException;

}
