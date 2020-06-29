package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dto.AddBookingDTO;
import com.upgrad.hirewheels.entities.Booking;
import com.upgrad.hirewheels.exceptions.GlobalExceptionHandler;

public interface BookingService {
    Booking addBooking(AddBookingDTO booking) throws GlobalExceptionHandler;
}
