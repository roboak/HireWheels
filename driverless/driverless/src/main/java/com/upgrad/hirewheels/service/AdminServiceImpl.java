package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dao.VehicleRepository;
import com.upgrad.hirewheels.dto.OptVehicleDTO;
import com.upgrad.hirewheels.entities.Activity;
import com.upgrad.hirewheels.entities.AdminRequest;
import com.upgrad.hirewheels.dao.AdminRequestRepository;
import com.upgrad.hirewheels.dao.RequestStatusRepository;
import com.upgrad.hirewheels.entities.RequestStatus;
import com.upgrad.hirewheels.exceptions.APIException;
import com.upgrad.hirewheels.responsemodel.AvailableRequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    AdminRequestRepository adminRequestRepository;

    @Autowired
    RequestStatusRepository requestStatusRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    public List<AvailableRequestResponse> getAllAdminRequest(int requestStatus) {
      List<AdminRequest> returnedRequests = requestStatusRepository.findByRequestStatusId(requestStatus).getAdminRequestList();
      List<AvailableRequestResponse> mappedList = new ArrayList<>();
      returnedRequests.forEach(a-> {
          AvailableRequestResponse availableRequestResponse = new AvailableRequestResponse();
          availableRequestResponse.setUserComments(a.getUserComments());
          availableRequestResponse.setAdminComments(a.getAdminComments());
          availableRequestResponse.setRequestId(a.getRequestId());
          availableRequestResponse.setUserId(a.getUser().getUserId());
          availableRequestResponse.setVehicleId(a.getVehicle().getVehicleId());
          availableRequestResponse.setActivityId(a.getActivity().getActivityId());
          availableRequestResponse.setRequestStatusId(a.getRequestStatus().getRequestStatusId());
          mappedList.add(availableRequestResponse);
      });
      return mappedList;
    }

    public Boolean updateRequest(OptVehicleDTO vehicle, int vehicleId) {
        AdminRequest returnedVehicle = vehicleRepository.findById(vehicleId).get().getAdminRequest();
        if( returnedVehicle == null){
            throw new APIException("Invalid Vehicle Id");
        }
        Activity activity = new Activity();
        activity.setActivityId(vehicle.getActivityId());
        returnedVehicle.setActivity(activity);
        RequestStatus requestStatus = new RequestStatus();
        requestStatus.setRequestStatusId(vehicle.getRequestStatusId());
        returnedVehicle.setRequestStatus(requestStatus);
        returnedVehicle.setUserComments(vehicle.getAdminComments());
        adminRequestRepository.save(returnedVehicle);
        return true;
    }
}
