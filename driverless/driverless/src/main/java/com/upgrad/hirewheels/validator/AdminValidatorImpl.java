package com.upgrad.hirewheels.validator;
import com.upgrad.hirewheels.dto.AdminActivityDTO;
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
    public void validateGetAllApprovals(int requestId) {
        if(!requestIds.contains(requestId)){
            throw new APIException("Not a Valid Request Id");
        }
    }

    @Override
    public void validateUpdateVehicleRequest(AdminActivityDTO vehicle, int vechileId) {
        if (vechileId == 0){
            throw new APIException("Not a Valid Vehicle Id");
        }
        if (!requestIds.contains(vehicle.getRequestStatusId())){
            throw new APIException("Not a Valid Status Id");
        }
        if (!activityIds.contains(vehicle.getActivityId())) {
            throw new APIException("Not a Valid Activity Id");
        }
    }
}
