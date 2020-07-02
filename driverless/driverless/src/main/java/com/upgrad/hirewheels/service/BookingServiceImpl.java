package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dao.LocationDAO;
import com.upgrad.hirewheels.dao.UserDAO;
import com.upgrad.hirewheels.dao.VehicleDAO;
import com.upgrad.hirewheels.dto.BookingDTO;
import com.upgrad.hirewheels.entities.Booking;
import com.upgrad.hirewheels.dao.BookingDAO;
import com.upgrad.hirewheels.entities.Users;
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
        Booking booking = new Booking();
        booking.setAmount(bookingDTO.getAmount());
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setPickUpDate(bookingDTO.getPickupDate());
        booking.setDropOffDate(bookingDTO.getDropoffDate());
        if (userDAO.findById(bookingDTO.getUserId()).get() == null){
            throw new APIException("Invalid User Id for Booking");
        } else {
            booking.setBookingWithUser(userDAO.findById(bookingDTO.getUserId()).get());
            Users user = userDAO.findById(bookingDTO.getUserId()).get();
            if (user.getWalletMoney() < bookingDTO.getAmount()) {
                throw new APIException("InSufficient Balance. Please Check With Admin.");
            } else {
                user.setWalletMoney(user.getWalletMoney() - bookingDTO.getAmount());
                userDAO.save(user);
            }
        }
        if (locationDAO.findById(bookingDTO.getLocationId()).get() == null){
            throw new APIException("Invalid Location Id for Booking");
        } else {
            booking.setLocationWithBooking(locationDAO.findById(bookingDTO.getLocationId()).get());
        }
        if (vehicleDAO.findById(bookingDTO.getVehicleId()).get() == null){
            throw new APIException("Invalid Vehicle Id for Booking");
        } else {
            booking.setVehicleWithBooking(vehicleDAO.findById(bookingDTO.getVehicleId()).get());
        }
        Booking savedBooking = bookingDAO.save(booking);
        return savedBooking;
    }

}
