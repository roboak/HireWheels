package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dao.LocationRepository;
import com.upgrad.hirewheels.dao.VehicleRepository;
import com.upgrad.hirewheels.dto.AddVehicleDTO;
import com.upgrad.hirewheels.dto.OptVehicleDTO;
import com.upgrad.hirewheels.entities.*;
import com.upgrad.hirewheels.dao.AdminRequestRepository;
import com.upgrad.hirewheels.exceptions.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    AdminRequestRepository adminRequestRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    LocationRepository locationRepository;

    public AdminRequest userOptRequest(OptVehicleDTO vehicle, int vehicleId) {
        AdminRequest returnedVehicle = vehicleRepository.findById(vehicleId).get().getAdminRequest();
        Activity activity = new Activity();
        activity.setActivityId(vehicle.getActivityId());
        returnedVehicle.setActivity(activity);
        RequestStatus requestStatus = new RequestStatus();
        requestStatus.setRequestStatusId(vehicle.getRequestStatusId());
        returnedVehicle.setRequestStatus(requestStatus);
        returnedVehicle.setUserComments(vehicle.getAdminComments());
        adminRequestRepository.save(returnedVehicle);
        return returnedVehicle;
    }

    public Vehicle addVehicleRequest(AddVehicleDTO vehicle, int userId) {
        Vehicle addVehicle = new Vehicle();
        AdminRequest adminRequest = new AdminRequest();
        String userRole = vehicle.getUserRole();
        addVehicle.setVehicleModel(vehicle.getVehicleModel());
        Users users = new Users();
        users.setUserId(userId);
        addVehicle.setUser(users);
        addVehicle.setVehicleNumber(vehicle.getVehicleNumber());
        addVehicle.setColor(vehicle.getColor());
        FuelType fuelType = new FuelType();
        fuelType.setFuelTypeId(vehicle.getFuelTypeId());
        addVehicle.setFuelType(fuelType);
        addVehicle.setCarImageUrl(vehicle.getCarImageUrl());
        if (locationRepository.findById(vehicle.getLocationId()).get() == null){
            throw new APIException("Invalid Location Id for Vehicle");
        } else {
            Location location = locationRepository.findById(vehicle.getLocationId()).get();
            addVehicle.setLocationWithVehicle(location);
        }
        VehicleSubCategory vehicleSubCategory = new VehicleSubCategory();
        vehicleSubCategory.setVehicleSubCategoryId(vehicle.getVehicleSubCategoryId());
        addVehicle.setVehicleSubCategory(vehicleSubCategory);
        Vehicle vehicle1 = vehicleRepository.save(addVehicle);
        Activity activity = new Activity();
        RequestStatus requestStatus = new RequestStatus();
        Users users1 = new Users();
        if (!userRole.equals("Admin")) {
            activity.setActivityId(201);
            adminRequest.setActivity(activity);
            requestStatus.setRequestStatusId(301);
            adminRequest.setRequestStatus(requestStatus);
            users1.setUserId(userId);
            adminRequest.setUser(users1);
            adminRequest.setUserComments("Kindly Approve My Vehicle.");
            adminRequest.setVehicle(vehicle1);
            adminRequestRepository.save(adminRequest);
        } else {
            activity.setActivityId(201);
            adminRequest.setActivity(activity);
            requestStatus.setRequestStatusId(302);
            adminRequest.setRequestStatus(requestStatus);
            users1.setUserId(userId);
            adminRequest.setUser(users);
            adminRequest.setAdminComments("Approved as added by Admin");
            adminRequest.setVehicle(vehicle1);
            adminRequestRepository.save(adminRequest);
        }
        return vehicle1;
    }
}
