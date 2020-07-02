package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dto.BookingDTO;
import com.upgrad.hirewheels.entities.Booking;
import com.upgrad.hirewheels.exceptions.advice.GlobalExceptionHandler;

import java.util.List;

public interface BookingService {
    Booking addBooking(BookingDTO booking) throws GlobalExceptionHandler;
    List<Booking> bookingHistory(int userId) throws GlobalExceptionHandler;
}
