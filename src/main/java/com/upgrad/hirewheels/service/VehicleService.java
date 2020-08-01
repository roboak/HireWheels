package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.entities.Vehicle;
import com.upgrad.hirewheels.exceptions.APIException;


import java.util.Date;
import java.util.List;

public interface VehicleService {
    public List<Vehicle> getAvailableVehicles(String categoryName, Date pickUpDate,Date dropDate, int locationId) throws APIException;
    public List<Vehicle> fetchAllVehicles() throws APIException;
}
