package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.constants.ActivityEnum;
import com.upgrad.hirewheels.constants.StatusEnum;
import com.upgrad.hirewheels.exceptions.APIException;
import com.upgrad.hirewheels.responsemodel.VehicleDetailResponse;
import com.upgrad.hirewheels.entities.*;
import com.upgrad.hirewheels.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    VehicleDAO vehicleDAO;

    @Autowired
    VehicleCategoryDAO vehicleCategoryDAO;

    @Autowired
    RequestStatusDAO requestStatusDAO;

    @Autowired
    AdminRequestDAO adminRequestDAO;

    @Autowired
    BookingDAO bookingDAO;

    @Autowired
    LocationDAO locationDAO;


    @Autowired
    FuelTypeDAO fuelTypeDAO;

    @Autowired
    CityDAO cityDAO;

    @Autowired
    UserDAO userDAO;


    /**
     * Returns all the available vehicle in the requested Category for booking with respect to Date, Location and Availability.
     * @param categoryName
     * @param pickUpDate
     * @param dropDate
     * @param locationId
     * @return
     */

    public List<VehicleDetailResponse> getAvailableVehicles(String categoryName, Date pickUpDate,Date dropDate, int locationId) {
        List<Vehicle> returnedVehicleList = new ArrayList<>();
            vehicleCategoryDAO.findByVehicleCategoryName(categoryName).getVehicleSubCategoriesList().forEach(a-> a.getVehicleList().forEach(b-> {
                if (b.getLocationWithVehicle().getLocationId() == locationId) {
                    returnedVehicleList.add(b);
                }
            }));
        List<Integer> bookedVehicleIdList = new ArrayList<>();
        returnedVehicleList.forEach(a-> {
            List<Booking> bookedVehicleList = bookingDAO.findByVehicleWithBooking(a);
            bookedVehicleList.forEach(b ->{
                if ((pickUpDate.after(b.getPickUpDate()) && pickUpDate.before(b.getDropOffDate())) || (dropDate.after(b.getPickUpDate()) && dropDate.before(b.getDropOffDate())) || (pickUpDate.before(b.getPickUpDate()) && dropDate.after(b.getDropOffDate())) || pickUpDate.equals(b.getDropOffDate()) || dropDate.equals(b.getPickUpDate()) || pickUpDate.equals(b.getPickUpDate()) || dropDate.equals(b.getDropOffDate())){
                    bookedVehicleIdList.add(b.getVehicleWithBooking().getVehicleId());
                }
            });
        });
        List<Integer> approvedVehicles = requestStatusDAO.findById(StatusEnum.APPROVED.getValue()).get().getAdminRequestList().stream().filter(a -> a.getActivity().getActivityId() != ActivityEnum.CAR_OPT_OUT.getValue()).map(AdminRequest::getVehicle).map(Vehicle::getVehicleId).collect(Collectors.toList());
        List<VehicleDetailResponse> mapVehicle = new ArrayList<>();
        for (Vehicle v : returnedVehicleList) {
            if (approvedVehicles.contains(v.getVehicleId())) {
                if(!bookedVehicleIdList.contains(v.getVehicleId())){
                    VehicleDetailResponse vehicleDetailResponse = new VehicleDetailResponse();
                    vehicleDetailResponse.setVehicleId(v.getVehicleId());
                    vehicleDetailResponse.setVehicleModel(v.getVehicleModel());
                    vehicleDetailResponse.setVehicleOwnerId(v.getUser().getUserId());
                    vehicleDetailResponse.setVehicleOwnerName(v.getUser().getFirstName());
                    vehicleDetailResponse.setVehicleNumber(v.getVehicleNumber());
                    vehicleDetailResponse.setColor(v.getColor());
                    vehicleDetailResponse.setCostPerHour(v.getVehicleSubCategory().getPricePerHour());
                    vehicleDetailResponse.setFuelType(v.getFuelType().getFuelType());
                    vehicleDetailResponse.setLocationId(v.getLocationWithVehicle().getLocationId());
                    vehicleDetailResponse.setCarImageUrl(v.getCarImageUrl());
                    vehicleDetailResponse.setActivityId(v.getAdminRequest().getActivity().getActivityId());
                    vehicleDetailResponse.setRequestStatusId(v.getAdminRequest().getRequestStatus().getRequestStatusId());
                    mapVehicle.add(vehicleDetailResponse);
                }
            }
        }
       return mapVehicle;
    }

    /**
     * Returns all the vehicle registered by user.
     * @param userId
     * @return
     */

    public List<VehicleDetailResponse> getAllVehicleByUserId(int userId) {
        List<VehicleDetailResponse> mapVehicle = new ArrayList<>();
        List<Vehicle> returnedVehicleList;
        if (userId != 1){
            returnedVehicleList = userDAO.findById(userId).get().getVehiclesList();
        } else {
            returnedVehicleList = vehicleDAO.findAll();
        }
        for (Vehicle v : returnedVehicleList) {
                VehicleDetailResponse vehicleDetailResponse = new VehicleDetailResponse();
                vehicleDetailResponse.setVehicleId(v.getVehicleId());
                vehicleDetailResponse.setVehicleModel(v.getVehicleModel());
                vehicleDetailResponse.setVehicleOwnerId(v.getUser().getUserId());
                vehicleDetailResponse.setVehicleNumber(v.getVehicleNumber());
                vehicleDetailResponse.setVehicleOwnerName(v.getUser().getFirstName());
                vehicleDetailResponse.setColor(v.getColor());
                vehicleDetailResponse.setCostPerHour(v.getVehicleSubCategory().getPricePerHour());
                vehicleDetailResponse.setFuelType(v.getFuelType().getFuelType());
                vehicleDetailResponse.setLocationId(v.getLocationWithVehicle().getLocationId());
                vehicleDetailResponse.setCarImageUrl(v.getCarImageUrl());
                vehicleDetailResponse.setActivityId(v.getAdminRequest().getActivity().getActivityId());
                vehicleDetailResponse.setRequestStatusId(v.getAdminRequest().getRequestStatus().getRequestStatusId());
                mapVehicle.add(vehicleDetailResponse);
        }
        return mapVehicle;
    }

}

