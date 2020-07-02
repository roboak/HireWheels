package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dao.*;
import com.upgrad.hirewheels.dto.VehicleDTO;
import com.upgrad.hirewheels.dto.AdminRequestDTO;
import com.upgrad.hirewheels.entities.*;
import com.upgrad.hirewheels.exceptions.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    AdminRequestDAO adminRequestDAO;

    @Autowired
    VehicleDAO vehicleDAO;

    @Autowired
    LocationDAO locationDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    ActivityDAO activityDAO;

    @Autowired
    RequestStatusDAO requestStatusDAO;

    /**
     * Helps User to OptIn/OptOut of their registered vehicle.
     * @param adminRequestDTO
     * @param vehicleId
     * @return
     */

    public AdminRequest changeAvailabilityRequest(AdminRequestDTO adminRequestDTO, int vehicleId) {
        AdminRequest adminRequest = vehicleDAO.findById(vehicleId).get().getAdminRequest();
        /**
         * To OptOut, vehicle must be in OptIn or Registered State with Approved Status
         */
        if ( adminRequestDTO.getActivityId() == 203){
            if(adminRequest.getActivity().getActivityId() != 202 && adminRequest.getRequestStatus().getRequestStatusId() != 302 || adminRequest.getActivity().getActivityId() != 201
                    && adminRequest.getRequestStatus().getRequestStatusId() != 302)
            {
                throw new APIException("Invalid OPT_OUT Request");
            }
        }
        /**
         * To OptIn, vehicle must be in OptOut State with Approved Status
         */
        if ( adminRequestDTO.getActivityId() == 202) {
            System.out.println(adminRequest.getActivity().getActivityId());
            System.out.println(adminRequest.getRequestStatus().getRequestStatusId());
            if(adminRequest.getActivity().getActivityId() != 203 && adminRequest.getRequestStatus().getRequestStatusId() != 302 || adminRequest.getActivity().getActivityId() != 201 && adminRequest.getRequestStatus().getRequestStatusId() != 302)
            {
                throw new APIException("OPT_IN Action Not Allowed");
            }
        }
        Activity activity = activityDAO.findById(adminRequestDTO.getActivityId()).get();
        adminRequest.setActivity(activity);
        RequestStatus requestStatus;
        if(adminRequestDTO.getUserId() != 1){
            requestStatus =  requestStatusDAO.findByRequestStatusId(301);
        } else {
            requestStatus =  requestStatusDAO.findByRequestStatusId(302);
        }
        adminRequest.setRequestStatus(requestStatus);
        adminRequest.setUserComments(adminRequestDTO.getUserComments());
        adminRequestDAO.save(adminRequest);
        return adminRequest;
    }

    /**
     * Helps to register a vehicle. Vehicle is auto approved if added by Admin. If added by admin, goes as a request for the Admin to approve.
     * @param vehicleDTO
     * @return
     */
    

    public Vehicle addVehicleRequest(VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();
        AdminRequest adminRequest = new AdminRequest();
        vehicle.setVehicleModel(vehicleDTO.getVehicleModel());
        Users user = new Users();
        user.setUserId(vehicleDTO.getUserId());
        vehicle.setUser(user);
        vehicle.setVehicleNumber(vehicleDTO.getVehicleNumber());
        vehicle.setColor(vehicleDTO.getColor());
        FuelType fuelType = new FuelType();
        fuelType.setFuelTypeId(vehicleDTO.getFuelTypeId());
        vehicle.setFuelType(fuelType);
        vehicle.setCarImageUrl(vehicleDTO.getCarImageUrl());
        Location location = locationDAO.findById(vehicleDTO.getLocationId()).get();
        vehicle.setLocationWithVehicle(location);
        VehicleSubCategory vehicleSubCategory = new VehicleSubCategory();
        vehicleSubCategory.setVehicleSubCategoryId(vehicleDTO.getVehicleSubCategoryId());
        vehicle.setVehicleSubCategory(vehicleSubCategory);
        Vehicle vehicle1 = vehicleDAO.save(vehicle);
        Activity activity = activityDAO.findById(201).get();
        adminRequest.setActivity(activity);
        RequestStatus requestStatus;
        adminRequest.setUser(user);
        if (vehicleDTO.getUserId() != 1) {
            requestStatus =  requestStatusDAO.findByRequestStatusId(301);
            adminRequest.setRequestStatus(requestStatus);
            adminRequest.setUserComments(vehicleDTO.getUserComments());
            adminRequest.setVehicle(vehicle1);
            adminRequestDAO.save(adminRequest);
        } else {
            requestStatus =  requestStatusDAO.findByRequestStatusId(302);
            adminRequest.setRequestStatus(requestStatus);
            adminRequest.setAdminComments("Approved as added by Admin");
            adminRequest.setVehicle(vehicle1);
            adminRequestDAO.save(adminRequest);
        }
        return vehicle;
    }
}
