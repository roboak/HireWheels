package com.upgrad.hirewheels.utils;

import com.upgrad.hirewheels.dto.BookingDTO;
import com.upgrad.hirewheels.dto.UserDTO;
import com.upgrad.hirewheels.dto.VehicleDTO;
import com.upgrad.hirewheels.entities.Booking;
import com.upgrad.hirewheels.entities.User;
import com.upgrad.hirewheels.entities.Vehicle;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * This is a utility class that contains methods to convert entity objects to DTO objects.
 * These DTO objects can then be sent back to the client in response bodies.
 */

@Component
public class EntityDTOConverter {

    public UserDTO convertToUserDTO(User user){

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setMobileNo(user.getMobileNo());
        userDTO.setWalletMoney(user.getWalletMoney());
        userDTO.setRoleName(user.getUserRole().getRoleName());
        return userDTO;
    }

    public List<VehicleDTO> convertToVehicleDTO(List<Vehicle> vehicleEntityList){

        List<VehicleDTO> vehicleDTOList = new ArrayList<>();
        vehicleEntityList.forEach(a->{
            VehicleDTO vehicleDTO = new VehicleDTO();
            vehicleDTO.setAvailability_status(a.getAvailabilityStatus());
            vehicleDTO.setCarImageUrl(a.getCarImageUrl());
            vehicleDTO.setColor(a.getColor());
            vehicleDTO.setFuelTypeId(a.getFuelType().getFuelTypeId());
            vehicleDTO.setLocationId(a.getLocationWithVehicle().getLocationId());
            vehicleDTO.setVehicleModel(a.getVehicleModel());
            vehicleDTO.setVehicleNumber(a.getVehicleNumber());
            vehicleDTO.setVehicleSubCategoryId(a.getVehicleSubCategory().getVehicleSubCategoryId());
            vehicleDTO.setVehicleId(a.getVehicleId());
            vehicleDTO.setPricePerDay(a.getVehicleSubCategory().getPricePerDay());
            vehicleDTOList.add(vehicleDTO);
        });
        return vehicleDTOList;
    }

    public BookingDTO convertToBookingDTO(Booking booking){

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setAmount(booking.getAmount());
        bookingDTO.setBookingDate(booking.getBookingDate());
        bookingDTO.setBookingId(booking.getBookingId());
        bookingDTO.setPickupDate(booking.getPickUpDate());
        bookingDTO.setDropoffDate(booking.getDropOffDate());
        bookingDTO.setLocationId(booking.getLocationWithBooking().getLocationId());
        bookingDTO.setUserId(booking.getBookingWithUser().getUserId());
        bookingDTO.setVehicleId(booking.getVehicleWithBooking().getVehicleId());
        return bookingDTO;
    }
}
