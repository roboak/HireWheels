package com.upgrad.hirewheels.validator;

import com.upgrad.hirewheels.dto.VehicleDTO;
import com.upgrad.hirewheels.dto.AdminRequestDTO;
import com.upgrad.hirewheels.exceptions.APIException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RequestValidatorImpl implements RequestValidator {

    List<Integer> activityIds = new ArrayList<>(Arrays.asList(201,202,203));

    @Override
    public void validateChangeVehicleAvailability(AdminRequestDTO adminRequestDTO, int vehicleId) {
        if (vehicleId == 0){
            throw new APIException("Vehicle Id can't be empty");
        }
        if (adminRequestDTO.getUserId() == 0){
            throw new APIException("User Id can't be empty");
        }
        if(!activityIds.contains(adminRequestDTO.getActivityId())){
            throw new APIException("Not a Valid Activity Id");
        }
    }

    @Override
    public void validateAddVehicleRequest(VehicleDTO vehicleDTO) {
        if(vehicleDTO.getVehicleNumber().isEmpty() || vehicleDTO.getVehicleNumber() == null){
            throw new APIException("Vehicle Number cannot be null or empty");
        }
        if(vehicleDTO.getVehicleModel().isEmpty() || vehicleDTO.getVehicleModel() == null){
            throw new APIException("Vehicle Model cannot be null or empty");
        }
        if (vehicleDTO.getVehicleSubCategoryId() == 0){
            throw new APIException("Sub-CategoryID can't be empty");
        }
        if(vehicleDTO.getColor().isEmpty() || vehicleDTO.getColor() == null){
            throw new APIException("Color cannot be null or empty");
        }
        if (vehicleDTO.getFuelTypeId() == 0){
            throw new APIException("FuelType can't be empty");
        }
        if (vehicleDTO.getLocationId() == 0){
            throw new APIException("LocationId can't be empty");
        }
        if(vehicleDTO.getUserId() == 0){
            throw new APIException("User Id can't be empty");
        }
        if(vehicleDTO.getCarImageUrl().isEmpty() || vehicleDTO.getCarImageUrl() == null){
            throw new APIException("CarImage URL cannot be null or empty");
        }
    }
}
