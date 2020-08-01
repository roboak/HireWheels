package com.upgrad.hirewheels.validator;

import com.upgrad.hirewheels.dto.VehicleDTO;
import com.upgrad.hirewheels.exceptions.APIException;
import org.springframework.stereotype.Component;


@Component
public class AdminRequestValidatorImpl implements AdminRequestValidator {

    @Override
    public void validateChangeVehicleAvailability(int availability_status) throws APIException{

        if( availability_status != 0 && availability_status != 1){
            throw new APIException("Invalid status value");
        }
    }

    @Override
    public void validateAddVehicleRequest(VehicleDTO vehicleDTO) throws APIException {
        if(vehicleDTO.getVehicleNumber().isEmpty() || vehicleDTO.getVehicleNumber() == null
                || vehicleDTO.getVehicleModel().isEmpty() || vehicleDTO.getVehicleModel() == null
                || vehicleDTO.getColor().isEmpty() || vehicleDTO.getColor() == null ||vehicleDTO.getCarImageUrl().isEmpty()
                || vehicleDTO.getCarImageUrl() == null || vehicleDTO.getVehicleSubCategoryId() == 0 || vehicleDTO.getFuelTypeId() == 0
                || vehicleDTO.getLocationId() == 0){
            throw new APIException("Fields shouldn’t be null or empty");
        }


    }
}
