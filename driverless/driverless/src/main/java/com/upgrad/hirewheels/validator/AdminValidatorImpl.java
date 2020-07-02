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
    List<Integer> activityIds = new ArrayList<>(Arrays.asList(201,202,203));

    @Override
    public void validateGetAllApprovals(int requestId) {
        if(!requestIds.contains(requestId)){
            throw new APIException("Not a Valid Status Id");
        }
    }

    @Override
    public void validateUpdateVehicleRequest(AdminActivityDTO adminActivityDTO, int vechileId) {
        if (adminActivityDTO.getUserId() != 1){
            throw new APIException("Only Admin Can Update Requests");
        }
        if (vechileId == 0){
            throw new APIException("Vehicle Id can't be empty");
        }
        if (!requestIds.contains(adminActivityDTO.getRequestStatusId())){
            throw new APIException("Not a Valid Status Id");
        }
        if (!activityIds.contains(adminActivityDTO.getActivityId())) {
            throw new APIException("Not a Valid Activity Id");
        }
    }
}
