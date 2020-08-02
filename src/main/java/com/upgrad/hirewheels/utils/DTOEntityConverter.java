package com.upgrad.hirewheels.utils;

import com.upgrad.hirewheels.dao.*;
import com.upgrad.hirewheels.dto.BookingDTO;
import com.upgrad.hirewheels.dto.UserDTO;
import com.upgrad.hirewheels.dto.VehicleDTO;
import com.upgrad.hirewheels.entities.Booking;
import com.upgrad.hirewheels.entities.User;
import com.upgrad.hirewheels.entities.Vehicle;
import com.upgrad.hirewheels.exceptions.InvalidLocationIdException;
import com.upgrad.hirewheels.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This is a utility class that contains methods to convert DTO objects to entity objects.
 * These objects can then be directly persisted into the database.
 */
@Component
public class DTOEntityConverter {

    @Autowired
    AdminService adminService;
    @Autowired
    VehicleSubCategoryDAO vehicleSubCategoryDAO;
    @Autowired
    LocationDAO locationDAO;
    @Autowired
    FuelTypeDAO fuelTypeDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    VehicleDAO vehicleDAO;
    @Autowired
    UserRoleDAO userRoleDAO;

    public Vehicle convertToVehicleEntity(VehicleDTO vehicleDTO) throws InvalidLocationIdException {
        locationDAO.findById(vehicleDTO.getLocationId()).
                orElseThrow(() -> new InvalidLocationIdException("Invalid Location Id"));
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleModel(vehicleDTO.getVehicleModel());
        vehicle.setVehicleNumber(vehicleDTO.getVehicleNumber());
        vehicle.setColor(vehicleDTO.getColor());
        vehicle.setFuelType(fuelTypeDAO.findById(vehicleDTO.getFuelTypeId()).get());
        vehicle.setCarImageUrl(vehicleDTO.getCarImageUrl());
        vehicle.setLocationWithVehicle(locationDAO.findById(vehicleDTO.getLocationId()).get());
        vehicle.setVehicleSubCategory(vehicleSubCategoryDAO.findById(vehicleDTO.getVehicleSubCategoryId()).get());
        vehicle.setAvailabilityStatus(1);
        return vehicle;
    }

    public Booking convertToBookingEntity(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setAmount(bookingDTO.getAmount());
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setPickUpDate(bookingDTO.getPickupDate());
        booking.setDropOffDate(bookingDTO.getDropoffDate());
        booking.setBookingWithUser(userDAO.findById(bookingDTO.getUserId()).get());
        booking.setLocationWithBooking(locationDAO.findById(bookingDTO.getLocationId()).get());
        booking.setVehicleWithBooking(vehicleDAO.findById(bookingDTO.getVehicleId()).get());
        return booking;
    }

    public User convertToUserEntity(UserDTO userDTO){
        User user = new User();
        user.setWalletMoney(10000);
        user.setUserRole(userRoleDAO.findByRoleId(2));
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setMobileNo(userDTO.getMobileNo());
        return user;
    }
}
