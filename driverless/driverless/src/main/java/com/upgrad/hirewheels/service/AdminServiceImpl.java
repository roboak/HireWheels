package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dao.ActivityDAO;
import com.upgrad.hirewheels.dao.VehicleDAO;
import com.upgrad.hirewheels.dto.AdminActivityDTO;
import com.upgrad.hirewheels.entities.Activity;
import com.upgrad.hirewheels.entities.AdminRequest;
import com.upgrad.hirewheels.dao.AdminRequestDAO;
import com.upgrad.hirewheels.dao.RequestStatusDAO;
import com.upgrad.hirewheels.entities.RequestStatus;
import com.upgrad.hirewheels.exceptions.APIException;
import com.upgrad.hirewheels.responsemodel.AdminRequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    AdminRequestDAO adminRequestDAO;

    @Autowired
    RequestStatusDAO requestStatusDAO;

    @Autowired
    VehicleDAO vehicleDAO;

    @Autowired
    ActivityDAO activityDAO;


    /**
     * Returns all the admin requests for approvals requested by User
     * @param statusId
     * @return
     */

    public List<AdminRequestResponse> getAllAdminRequest(int statusId) {
      List<AdminRequest> returnedRequests = requestStatusDAO.findByRequestStatusId(statusId).getAdminRequestList();
      List<AdminRequestResponse> mappedList = new ArrayList<>();
      returnedRequests.forEach(a-> {
          AdminRequestResponse adminRequestResponse = new AdminRequestResponse();
          adminRequestResponse.setUserComments(a.getUserComments());
          adminRequestResponse.setAdminComments(a.getAdminComments());
          adminRequestResponse.setRequestId(a.getRequestId());
          adminRequestResponse.setVehicleId(a.getVehicle().getVehicleId());
          adminRequestResponse.setVehicleModel(a.getVehicle().getVehicleModel());
          adminRequestResponse.setVehicleNumber(a.getVehicle().getVehicleNumber());
          adminRequestResponse.setCarImageUrl(a.getVehicle().getCarImageUrl());
          adminRequestResponse.setActivityId(a.getActivity().getActivityId());
          adminRequestResponse.setRequestStatusId(a.getRequestStatus().getRequestStatusId());
          mappedList.add(adminRequestResponse);
      });
      return mappedList;
    }

    /**
     * Updates the request status of any pending approvals to APPROVED or REJECTED
     * @param adminActivityDTO
     * @param requestId
     * @return
     */

    public Boolean updateRequest(AdminActivityDTO adminActivityDTO, int requestId) {
        AdminRequest adminRequest = adminRequestDAO.findById(requestId).get();
        Activity activity = activityDAO.findById(adminActivityDTO.getActivityId()).get();
        adminRequest.setActivity(activity);
        RequestStatus requestStatus = requestStatusDAO.findByRequestStatusId(adminActivityDTO.getRequestStatusId());
        adminRequest.setRequestStatus(requestStatus);
        adminRequest.setAdminComments(adminActivityDTO.getAdminComments());
        adminRequestDAO.save(adminRequest);
        return true;
    }
}
