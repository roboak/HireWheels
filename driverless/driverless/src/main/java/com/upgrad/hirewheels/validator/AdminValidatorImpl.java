package com.upgrad.hirewheels.validator;
import com.upgrad.hirewheels.dto.OptVehicleDTO;
import com.upgrad.hirewheels.exceptions.APIException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AdminValidatorImpl implements AdminValidator{

    List<Integer> requestIds = new ArrayList<>(Arrays.asList(301,302,303));
    List<Integer> activityIds = new ArrayList<>(Arrays.asList(201,202,203,204));

    @Override
    public void validateApprovals(int requestId) {
        if(!requestIds.contains(requestId)){
            throw new APIException("Not a Valid Request Id");
        }
        if(requestId == 0 || String.valueOf(requestId).length()<3 || String.valueOf(requestId).length()>3){
            throw new APIException("Request Id cannot be null or empty and must be a three digit");
        }
    }

    @Override
    public void validateAddVehicleRequest(OptVehicleDTO vehicle, int vechileId) {
        if (vechileId == 0){
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
}
