package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dao.LocationDAO;
import com.upgrad.hirewheels.dao.VehicleDAO;
import com.upgrad.hirewheels.dto.VehicleDTO;
import com.upgrad.hirewheels.dto.AdminRequestDTO;
import com.upgrad.hirewheels.entities.*;
import com.upgrad.hirewheels.dao.AdminRequestDAO;
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

    /**
     * Helps User to OptIn/OptOut of their registered vehicle.
     * @param requestDTO
     * @param vehicleId
     * @return
     */

    public AdminRequest changeAvailabilityRequest(AdminRequestDTO requestDTO, int vehicleId) {
        AdminRequest returnedVehicle = vehicleDAO.findById(vehicleId).get().getAdminRequest();
        if( returnedVehicle == null){
            throw new APIException("Invalid Vehicle Id");
        }
        /**
         * To OptOut, vehicle must be in OptIn or Registered State with Approved Status
         */
        if (requestDTO.getRequestStatusId() == 203){
            if(returnedVehicle.getRequestStatus().getRequestStatusId() != 202 || returnedVehicle.getRequestStatus().getRequestStatusId() != 201
                    && returnedVehicle.getActivity().getActivityId() != 302)
            {
                throw new APIException("Invalid OPT_OUT Request");
            }
        }
        /**
         * To OptIn, vehicle must be in OptOut State with Approved Status
         */
        if (requestDTO.getRequestStatusId() == 202){
            if(returnedVehicle.getRequestStatus().getRequestStatusId() != 203 && returnedVehicle.getActivity().getActivityId() != 302)
            {
                throw new APIException("OPT_IN Action Not Allowed");
            }
        }
        Activity activity = new Activity();
        activity.setActivityId(requestDTO.getActivityId());
        returnedVehicle.setActivity(activity);
        RequestStatus requestStatus = new RequestStatus();
        requestStatus.setRequestStatusId(requestDTO.getRequestStatusId());
        returnedVehicle.setRequestStatus(requestStatus);
        returnedVehicle.setUserComments(requestDTO.getAdminComments());
        adminRequestDAO.save(returnedVehicle);
        return returnedVehicle;
    }

    /**
     * Helps to register a vehicle. Vehicle is auto approved if added by Admin. If added by admin, goes as a request for the Admin to approve.
     * @param vehicleDTO
     * @return
     */
    

    public Vehicle addVehicleRequest(VehicleDTO vehicleDTO) {
        Vehicle addVehicle = new Vehicle();
        AdminRequest adminRequest = new AdminRequest();
        addVehicle.setVehicleModel(vehicleDTO.getVehicleModel());
        Users users = new Users();
        users.setUserId(vehicleDTO.getUserId());
        addVehicle.setUser(users);
        addVehicle.setVehicleNumber(vehicleDTO.getVehicleNumber());
        addVehicle.setColor(vehicleDTO.getColor());
        FuelType fuelType = new FuelType();
        fuelType.setFuelTypeId(vehicleDTO.getFuelTypeId());
        addVehicle.setFuelType(fuelType);
        addVehicle.setCarImageUrl(vehicleDTO.getCarImageUrl());
        if (locationDAO.findById(vehicleDTO.getLocationId()).get() == null){
            throw new APIException("Invalid Location Id for Vehicle");
        } else {
            Location location = locationDAO.findById(vehicleDTO.getLocationId()).get();
            addVehicle.setLocationWithVehicle(location);
        }
        VehicleSubCategory vehicleSubCategory = new VehicleSubCategory();
        vehicleSubCategory.setVehicleSubCategoryId(vehicleDTO.getVehicleSubCategoryId());
        addVehicle.setVehicleSubCategory(vehicleSubCategory);
        Vehicle vehicle = vehicleDAO.save(addVehicle);
        Activity activity = new Activity();
        RequestStatus requestStatus = new RequestStatus();
        activity.setActivityId(201);
        adminRequest.setActivity(activity);
        adminRequest.setUser(users);
        if (vehicleDTO.getUserId() != 1) {
            requestStatus.setRequestStatusId(301);
            adminRequest.setRequestStatus(requestStatus);
            adminRequest.setUserComments("Kindly Approve My Vehicle.");
            adminRequest.setVehicle(vehicle);
            adminRequestDAO.save(adminRequest);
        } else {
            requestStatus.setRequestStatusId(302);
            adminRequest.setRequestStatus(requestStatus);
            adminRequest.setAdminComments("Approved as added by Admin");
            adminRequest.setVehicle(vehicle);
            adminRequestDAO.save(adminRequest);
        }
        return vehicle;
    }
}
