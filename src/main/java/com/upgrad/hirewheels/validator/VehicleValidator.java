package com.upgrad.hirewheels.validator;

import com.upgrad.hirewheels.exceptions.APIException;

import java.text.ParseException;
import java.util.Date;

public interface VehicleValidator {
    void validategetAllVehicles(String categoryName, Date pickupDate, Date dropDate, int locationId) throws APIException, ParseException;
    void validateUser(int userId) throws APIException;
}
