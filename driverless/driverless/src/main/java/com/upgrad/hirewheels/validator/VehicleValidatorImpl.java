package com.upgrad.hirewheels.validator;

import com.upgrad.hirewheels.exceptions.APIException;
import com.upgrad.hirewheels.exceptions.GlobalExceptionHandler;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class VehicleValidatorImpl implements VehicleValidator {
    @Override
    public void validategetAllVehicles(String categoryName, Date pickupDate, Date dropDate, int locationId) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(new Date());
        Date todaysDate = sdf.parse(dateString);
        String formatPickUpDate = sdf.format(pickupDate);
        Date parsedPickUpDate = sdf.parse(formatPickUpDate);
        String formatDropOffDate = sdf.format(dropDate);
        Date parsedDropOffDate = sdf.parse(formatDropOffDate);
        if(categoryName.isEmpty() || categoryName == null){
            throw new APIException("Category Name cannot be null or empty");
        }
        if (locationId == 0){
            throw new APIException("Location Id cannot be empty or null");
        }
        if (todaysDate.compareTo(parsedDropOffDate) != -1 || parsedPickUpDate.compareTo(parsedDropOffDate) != -1){
            throw new APIException("DropDate should be greater than today's date and greater than PickUp Date");
        }
        if (todaysDate.compareTo(parsedPickUpDate) == 1){
            throw new APIException("PickUpDate should not be less than today's date");
        }
    }

    @Override
    public void validateUser(int userId) {
        if (userId == 0){
            throw new APIException("User Id cannot be empty or null");
        }
    }
}