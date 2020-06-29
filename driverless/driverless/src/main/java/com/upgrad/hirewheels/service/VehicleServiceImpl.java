package com.upgrad.hirewheels.service;

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
    VehicleRepository vehicleRepository;

    @Autowired
    VehicleCategoryRepo vehicleCategoryRepo;

    @Autowired
    RequestStatusRepository requestStatusRepository;

    @Autowired
    AdminRequestRepository adminRequestRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    LocationRepository locationRepository;


    @Autowired
    FuelTypeRepository fuelTypeRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    UserRepository userRepository;


    public List<VehicleDetailResponse> getAvailableVehicles(String categoryName, Date pickUpDate,Date dropDate, int locationId) {
        List<Vehicle> returnedVehicleList = new ArrayList<>();
        if(vehicleCategoryRepo.findByVehicleCategoryName(categoryName) != null){
            vehicleCategoryRepo.findByVehicleCategoryName(categoryName).getVehicleSubCategories().forEach(a-> a.getVehicle().forEach(b-> {
                if (b.getLocationWithVehicle().getLocationId() == locationId) {
                    returnedVehicleList.add(b);
                }
            }));
        } else {
            throw new APIException("Invalid Vehicle Category Name");
        }

        List<Integer> bookedVehicles = new ArrayList<>();
        bookingRepository.findByPickUpDateGreaterThanEqualAndDropOffDateLessThanEqual(pickUpDate, dropDate).stream().forEach(a-> {bookedVehicles.add(a.getVehicleWithBooking().getVehicleId());});
        List<Integer> approvedVehicles = requestStatusRepository.findById(302).get().getAdminRequestList().stream().filter(a -> a.getActivity().getActivityId() != 204).map(AdminRequest::getVehicle).map(Vehicle::getVehicleId).collect(Collectors.toList());
        List<VehicleDetailResponse> mapVehicle = new ArrayList<>();
        for (Vehicle v : returnedVehicleList) {
            if (approvedVehicles.contains(v.getVehicleId())) {
                if(!bookedVehicles.contains(v.getVehicleId())){
                    VehicleDetailResponse y = new VehicleDetailResponse();
                    y.setVehicleId(v.getVehicleId());
                    y.setVehicleModel(v.getVehicleModel());
                    y.setVehicleOwner(v.getUser().getUserId());
                    y.setVehicleNumber(v.getVehicleNumber());
                    y.setColor(v.getColor());
                    y.setCostPerHour(v.getVehicleSubCategory().getPricePerHour());
                    y.setFuelType(v.getFuelType().getFuelType());
                    y.setLocationId(v.getLocationWithVehicle().getLocationId());
                    y.setLocationName(v.getLocationWithVehicle().getLocationName());
                    y.setAddress(v.getLocationWithVehicle().getAddress());
                    y.setPincode(v.getLocationWithVehicle().getPincode());
                    y.setCarImageUrl(v.getCarImageUrl());
                    y.setCityName(v.getLocationWithVehicle().getCity().getCityName());
                    mapVehicle.add(y);
                }
            }
        }
       return mapVehicle;
    }

    public List<VehicleDetailResponse> getAllVehicleByUserId(int userId) {
        List<VehicleDetailResponse> mapVehicle = new ArrayList<>();
        List<Vehicle> returnedVehicleList = userRepository.findById(userId).get().getVehiclesList();
        if (returnedVehicleList == null){
            throw new APIException("Invalid UserId");

        }
        for (Vehicle v : returnedVehicleList) {
                VehicleDetailResponse y = new VehicleDetailResponse();
                y.setVehicleId(v.getVehicleId());
                y.setVehicleModel(v.getVehicleModel());
                y.setVehicleOwner(v.getUser().getUserId());
                y.setVehicleNumber(v.getVehicleNumber());
                y.setColor(v.getColor());
                y.setCostPerHour(v.getVehicleSubCategory().getPricePerHour());
                y.setFuelType(v.getFuelType().getFuelType());
                y.setLocationId(v.getLocationWithVehicle().getLocationId());
                y.setLocationName(v.getLocationWithVehicle().getLocationName());
                y.setAddress(v.getLocationWithVehicle().getAddress());
                y.setPincode(v.getLocationWithVehicle().getPincode());
                y.setCarImageUrl(v.getCarImageUrl());
                y.setCityName(v.getLocationWithVehicle().getCity().getCityName());
                y.setActivityId(v.getAdminRequest().getActivity().getActivityId());
                y.setRequestStatusId(v.getAdminRequest().getRequestStatus().getRequestStatusId());
                mapVehicle.add(y);
        }
        return mapVehicle;
    }

}

