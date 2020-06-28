package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dao.LocationRepository;
import com.upgrad.hirewheels.dao.UserRepository;
import com.upgrad.hirewheels.dao.VehicleRepository;
import com.upgrad.hirewheels.dto.AddBookingDTO;
import com.upgrad.hirewheels.entities.Booking;
import com.upgrad.hirewheels.dao.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    public Booking addBooking(AddBookingDTO booking){
        Booking booking1 = new Booking();
        booking1.setAmount(booking.getAmount());
        booking1.setBookingDate(booking.getBookingDate());
        booking1.setPickUpDate(booking.getPickupDate());
        booking1.setDropOffDate(booking.getDropoffDate());
        booking1.setBookingWithUser(userRepository.findById(booking.getUserId()).get());
        booking1.setLocationWithBooking(locationRepository.findById(booking.getLocationId()).get());
        booking1.setVehicleWithBooking(vehicleRepository.findById(booking.getVehicleId()).get());
        Booking successBooking = bookingRepository.save(booking1);
        return successBooking;
    }

}
