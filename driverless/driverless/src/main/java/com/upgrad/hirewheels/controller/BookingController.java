package com.upgrad.hirewheels.controller;

import com.upgrad.hirewheels.dto.AddBookingDTO;
import com.upgrad.hirewheels.entities.Booking;
import com.upgrad.hirewheels.exceptions.GlobalExceptionHandler;
import com.upgrad.hirewheels.service.BookingService;
import com.upgrad.hirewheels.validator.BookingValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    BookingValidator bookingValidator;

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @PostMapping("/bookVehicle")
    public ResponseEntity booking(@RequestBody AddBookingDTO vehicle) {
        ResponseEntity responseEntity = null;
        try {
            bookingValidator.validateBooking(vehicle);
            Booking responseBooking = bookingService.addBooking(vehicle);
            responseEntity = ResponseEntity.ok(responseBooking);
        } catch (ParseException | GlobalExceptionHandler e){
            logger.error(e.getMessage());
        }
        return responseEntity;
    }
}
