package com.upgrad.hirewheels.validator;

import com.upgrad.hirewheels.dto.AddVehicleDTO;
import com.upgrad.hirewheels.dto.OptVehicleDTO;
import com.upgrad.hirewheels.exceptions.APIException;
import com.upgrad.hirewheels.exceptions.GlobalExceptionHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserRequestValidatorImpl implements UserRequestValidator {

    List<Integer> requestIds = new ArrayList<>(Arrays.asList(301,302,303));
    List<Integer> activityIds = new ArrayList<>(Arrays.asList(201,202,203));

    @Override
    public void validateOptVehicleRequest(OptVehicleDTO vehicle, int vehicleId) {
        if (vehicleId == 0){
            throw new APIException("Vehicle Id cannot be empty or null");
        }
        if(!requestIds.contains(vehicle.getRequestStatusId())){
            throw new APIException("Not a Valid Request Id");
        }
        if(vehicle.getRequestStatusId() == 0 || String.valueOf(vehicle.getRequestStatusId()).length()<3 || String.valueOf(vehicle.getRequestStatusId()).length()>3){
            throw new APIException("Request Id cannot be null or empty and must be a three digit");
        }
        if(!activityIds.contains(vehicle.getActivityId())){
            throw new APIException("Not a Valid Activity Id");
        }
        if(vehicle.getActivityId() == 0 || String.valueOf(vehicle.getActivityId()).length()<3 || String.valueOf(vehicle.getActivityId()).length()>3){
            throw new APIException("Activity Id cannot be null or empty and must be a three digit");
        }
    }

    @Override
    public void validateAddVehicleRequest(AddVehicleDTO vehicleDTO, int userId) {
        if (userId == 0){
            throw new APIException("UserId Id cannot be empty or null");
        }
        if(vehicleDTO.getVehicleNumber().isEmpty() || vehicleDTO.getVehicleNumber() == null){
            throw new APIException("Vehicle Number cannot be null or empty");
        }
        if(vehicleDTO.getVehicleModel().isEmpty() || vehicleDTO.getVehicleModel() == null){
            throw new APIException("Vehicle Model cannot be null or empty");
        }
        if (vehicleDTO.getVehicleSubCategoryId() == 0){
            throw new APIException("Vehicle SubCategoryId cannot be empty or null");
        }
        if(vehicleDTO.getColor().isEmpty() || vehicleDTO.getColor() == null){
            throw new APIException("Color cannot be null or empty");
        }
        if (vehicleDTO.getCostPerHour() == 0){
            throw new APIException("CostPerHour cannot be empty or null");
        }
        if (vehicleDTO.getFuelTypeId() == 0){
            throw new APIException("FuelType cannot be empty or null");
        }
        if (vehicleDTO.getLocationId() == 0){
            throw new APIException("LocationId cannot be empty or null");
        }
        if (vehicleDTO.getCityId() == 0){
            throw new APIException("CityId cannot be empty or null");
        }
        if(vehicleDTO.getUserRole().isEmpty() || vehicleDTO.getUserRole() == null){
            throw new APIException("userRole cannot be null or empty");
        }
        if(vehicleDTO.getCarImageUrl().isEmpty() || vehicleDTO.getCarImageUrl() == null){
            throw new APIException("CarImage URL cannot be null or empty");
        }
    }
}
