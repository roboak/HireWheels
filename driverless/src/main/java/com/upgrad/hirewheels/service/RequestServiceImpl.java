package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.constants.ActivityEnum;
import com.upgrad.hirewheels.constants.StatusEnum;
import com.upgrad.hirewheels.dao.*;
import com.upgrad.hirewheels.dto.AdminRequestDTO;
import com.upgrad.hirewheels.dto.VehicleDTO;
import com.upgrad.hirewheels.entities.AdminRequest;
import com.upgrad.hirewheels.entities.Vehicle;
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
        if ( adminRequestDTO.getActivityId() == ActivityEnum.CAR_OPT_OUT.getValue()){
            if(adminRequest.getActivity().getActivityId() != ActivityEnum.CAR_OPT_IN.getValue() && adminRequest.getRequestStatus().getRequestStatusId() != StatusEnum.APPROVED.getValue() || adminRequest.getActivity().getActivityId() != ActivityEnum.VEHICLE_REGISTER.getValue()
                    && adminRequest.getRequestStatus().getRequestStatusId() != StatusEnum.APPROVED.getValue())
            {
                throw new APIException("Invalid OPT_OUT Request");
            }
        }
        /**
         * To OptIn, vehicle must be in OptOut State with Approved Status
         */
        if ( adminRequestDTO.getActivityId() == ActivityEnum.CAR_OPT_IN.getValue()) {
            if(adminRequest.getActivity().getActivityId() != ActivityEnum.CAR_OPT_OUT.getValue() && adminRequest.getRequestStatus().getRequestStatusId() != StatusEnum.APPROVED.getValue() || adminRequest.getActivity().getActivityId() != ActivityEnum.VEHICLE_REGISTER.getValue() && adminRequest.getRequestStatus().getRequestStatusId() != StatusEnum.APPROVED.getValue())
            {
                throw new APIException("OPT_IN Action Not Allowed");
            }
        }
        adminRequest.setActivity(activityDAO.findById(adminRequestDTO.getActivityId()).get());
        if(adminRequestDTO.getUserId() != 1){
            adminRequest.setRequestStatus(requestStatusDAO.findByRequestStatusId(StatusEnum.PENDING.getValue()));
            adminRequest.setUserComments(adminRequestDTO.getUserComments());
            adminRequest.setAdminComments(adminRequest.getAdminComments());
        } else {
            adminRequest.setRequestStatus(requestStatusDAO.findByRequestStatusId(StatusEnum.APPROVED.getValue()));
            adminRequest.setAdminComments(adminRequestDTO.getAdminComments());
            adminRequest.setUserComments(adminRequest.getUserComments());
        }
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
        adminRequest.setActivity(activityDAO.findById(ActivityEnum.VEHICLE_REGISTER.getValue()).get());
        adminRequest.setUser(userDAO.findById(vehicleDTO.getUserId()).get());
        if (vehicleDTO.getUserId() != 1) {
            adminRequest.setRequestStatus(requestStatusDAO.findByRequestStatusId(StatusEnum.PENDING.getValue()));
            adminRequest.setUserComments(vehicleDTO.getUserComments());
            adminRequest.setVehicle(vehicle1);
            adminRequestDAO.save(adminRequest);
        } else {
            adminRequest.setRequestStatus(requestStatusDAO.findByRequestStatusId(StatusEnum.APPROVED.getValue()));
            adminRequest.setAdminComments("Approved as added by Admin");
            adminRequest.setVehicle(vehicle1);
            adminRequestDAO.save(adminRequest);
        }
        return vehicle;
    }
}
