package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dao.*;
import com.upgrad.hirewheels.dto.VehicleDTO;
import com.upgrad.hirewheels.dto.AdminRequestDTO;
import com.upgrad.hirewheels.entities.*;
import com.upgrad.hirewheels.exceptions.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    FuelTypeDAO fuelTypeDAO;

    @Autowired
    VehicleSubCategoryDAO vehicleSubCategoryDAO;

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
            if(adminRequest.getActivity().getActivityId() != 203 && adminRequest.getRequestStatus().getRequestStatusId() != 302 || adminRequest.getActivity().getActivityId() != 201 && adminRequest.getRequestStatus().getRequestStatusId() != 302)
            {
                throw new APIException("OPT_IN Action Not Allowed");
            }
        }
        adminRequest.setActivity(activityDAO.findById(adminRequestDTO.getActivityId()).get());
        if(adminRequestDTO.getUserId() != 1){
            adminRequest.setRequestStatus(requestStatusDAO.findByRequestStatusId(301));
        } else {
            adminRequest.setRequestStatus(requestStatusDAO.findByRequestStatusId(302));
        }
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
        boolean testVehicleNumber = vehicleDAO.existsByVehicleNumber(vehicleDTO.getVehicleNumber());
        if (testVehicleNumber) {
            throw new APIException("Vehicle Number Already Exists");
        }
        Vehicle vehicle = new Vehicle();
        AdminRequest adminRequest = new AdminRequest();
        vehicle.setVehicleModel(vehicleDTO.getVehicleModel());
        vehicle.setUser(userDAO.findById(vehicleDTO.getUserId()).get());
        vehicle.setVehicleNumber(vehicleDTO.getVehicleNumber());
        vehicle.setColor(vehicleDTO.getColor());
        vehicle.setFuelType(fuelTypeDAO.findById(vehicleDTO.getFuelTypeId()).get());
        vehicle.setCarImageUrl(vehicleDTO.getCarImageUrl());
        vehicle.setLocationWithVehicle(locationDAO.findById(vehicleDTO.getLocationId()).get());
        vehicle.setVehicleSubCategory(vehicleSubCategoryDAO.findById(vehicleDTO.getVehicleSubCategoryId()).get());
        Vehicle vehicle1 = vehicleDAO.save(vehicle);
        adminRequest.setActivity(activityDAO.findById(201).get());
        adminRequest.setUser(userDAO.findById(vehicleDTO.getUserId()).get());
        if (vehicleDTO.getUserId() != 1) {
            adminRequest.setRequestStatus(requestStatusDAO.findByRequestStatusId(301));
            adminRequest.setUserComments(vehicleDTO.getUserComments());
            adminRequest.setVehicle(vehicle1);
            adminRequestDAO.save(adminRequest);
        } else {
            adminRequest.setRequestStatus(requestStatusDAO.findByRequestStatusId(302));
            adminRequest.setAdminComments("Approved as added by Admin");
            adminRequest.setVehicle(vehicle1);
            adminRequestDAO.save(adminRequest);
        }
        return vehicle;
    }
}
