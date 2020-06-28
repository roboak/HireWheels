package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dto.AddBookingDTO;
import com.upgrad.hirewheels.entities.Booking;

public interface BookingService {
    Booking addBooking(AddBookingDTO booking);
}
