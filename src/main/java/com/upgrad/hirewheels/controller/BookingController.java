package com.upgrad.hirewheels.controller;

import com.upgrad.hirewheels.dto.BookingDTO;
import com.upgrad.hirewheels.entities.Booking;
import com.upgrad.hirewheels.exceptions.APIException;
import com.upgrad.hirewheels.exceptions.InsufficientBalanceException;
import com.upgrad.hirewheels.service.BookingService;
import com.upgrad.hirewheels.utils.DTOEntityConverter;
import com.upgrad.hirewheels.utils.EntityDTOConverter;
import com.upgrad.hirewheels.validator.BookingValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;

@RestController
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    BookingValidator bookingValidator;

    @Autowired
    DTOEntityConverter dtoEntityConverter;

    @Autowired
    EntityDTOConverter entityDTOConverter;

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @PostMapping("/bookings")
    public ResponseEntity addBooking(@RequestBody BookingDTO bookingDTO) throws APIException, ParseException, InsufficientBalanceException {
        ResponseEntity responseEntity = null;
        bookingValidator.validateBooking(bookingDTO);
        Booking booking = dtoEntityConverter.convertToBookingEntity(bookingDTO);
        Booking responseBooking = bookingService.addBooking(booking);
        responseEntity = ResponseEntity.ok(entityDTOConverter.convertToBookingDTO(responseBooking));
        return responseEntity;
    }




}
