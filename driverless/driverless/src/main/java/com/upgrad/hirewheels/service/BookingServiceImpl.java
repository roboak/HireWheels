package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dao.LocationDAO;
import com.upgrad.hirewheels.dao.UserDAO;
import com.upgrad.hirewheels.dao.VehicleDAO;
import com.upgrad.hirewheels.dto.BookingDTO;
import com.upgrad.hirewheels.entities.Booking;
import com.upgrad.hirewheels.dao.BookingDAO;
import com.upgrad.hirewheels.exceptions.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingDAO bookingDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    LocationDAO locationDAO;

    @Autowired
    VehicleDAO vehicleDAO;

    /**
     * Helps in adding a bookingDTO for a Vehicle with respect to valid userId,LocationId and BookingId
     * @param bookingDTO
     * @return
     */

    public Booking addBooking(BookingDTO bookingDTO){
        Booking booking1 = new Booking();
        booking1.setAmount(bookingDTO.getAmount());
        booking1.setBookingDate(bookingDTO.getBookingDate());
        booking1.setPickUpDate(bookingDTO.getPickupDate());
        booking1.setDropOffDate(bookingDTO.getDropoffDate());
        if (userDAO.findById(bookingDTO.getUserId()).get() == null){
            throw new APIException("Invalid User Id for Booking");
        } else {
            booking1.setBookingWithUser(userDAO.findById(bookingDTO.getUserId()).get());
        }
        if (locationDAO.findById(bookingDTO.getLocationId()).get() == null){
            throw new APIException("Invalid Location Id for Booking");
        } else {
            booking1.setLocationWithBooking(locationDAO.findById(bookingDTO.getLocationId()).get());
        }
        if (vehicleDAO.findById(bookingDTO.getVehicleId()).get() == null){
            throw new APIException("Invalid Vehicle Id for Booking");
        } else {
            booking1.setVehicleWithBooking(vehicleDAO.findById(bookingDTO.getVehicleId()).get());
        }
        Booking returnResponse = bookingDAO.save(booking1);
        return returnResponse;
    }

}
