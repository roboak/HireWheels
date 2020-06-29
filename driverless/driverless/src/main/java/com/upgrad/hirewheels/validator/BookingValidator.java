package com.upgrad.hirewheels.validator;

import com.upgrad.hirewheels.dto.AddBookingDTO;

import java.text.ParseException;

public interface BookingValidator {
    void validateBooking(AddBookingDTO vehicle) throws ParseException;
}
