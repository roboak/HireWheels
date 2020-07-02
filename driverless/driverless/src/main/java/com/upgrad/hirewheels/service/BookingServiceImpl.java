package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dao.LocationDAO;
import com.upgrad.hirewheels.dao.UserDAO;
import com.upgrad.hirewheels.dao.VehicleDAO;
import com.upgrad.hirewheels.dto.BookingDTO;
import com.upgrad.hirewheels.entities.Booking;
import com.upgrad.hirewheels.dao.BookingDAO;
import com.upgrad.hirewheels.entities.Users;
import com.upgrad.hirewheels.entities.Vehicle;
import com.upgrad.hirewheels.exceptions.APIException;
import com.upgrad.hirewheels.responsemodel.BookingHistoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

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
        booking.setBookingWithUser(userDAO.findById(bookingDTO.getUserId()).get());
        Users user = userDAO.findById(bookingDTO.getUserId()).get();
        if (user.getWalletMoney() < bookingDTO.getAmount()) {
            throw new APIException("InSufficient Balance. Please Check With Admin.");
        } else {
            user.setWalletMoney(user.getWalletMoney() - bookingDTO.getAmount());
            userDAO.save(user);
        }
        booking.setLocationWithBooking(locationDAO.findById(bookingDTO.getLocationId()).get());
        booking.setVehicleWithBooking(vehicleDAO.findById(bookingDTO.getVehicleId()).get());
        Booking savedBooking = bookingDAO.save(booking);
        return savedBooking;
    }

    /**
     * Returns the booking history of user
     * @param userId
     * @return
     */

    @Override
    public List<BookingHistoryResponse> bookingHistory(int userId) {
        List<BookingHistoryResponse> bookingHistoryResponseList = new ArrayList<>();
        userDAO.findById(userId).get().getBookingList().forEach(a-> {
            BookingHistoryResponse bookingHistoryResponse = new BookingHistoryResponse();
            bookingHistoryResponse.setBookingId(a.getBookingId());
            bookingHistoryResponse.setBookingDate(a.getBookingDate());
            bookingHistoryResponse.setDropOffDate(a.getDropOffDate());
            bookingHistoryResponse.setPickUpDate(a.getPickUpDate());
            bookingHistoryResponse.setAmount(a.getAmount());
            bookingHistoryResponse.setVehicleId(a.getVehicleWithBooking().getVehicleId());
            bookingHistoryResponse.setVehicleNumber(a.getVehicleWithBooking().getVehicleNumber());
            bookingHistoryResponse.setVehicleModel(a.getVehicleWithBooking().getVehicleModel());
            bookingHistoryResponse.setLocationName(a.getLocationWithBooking().getLocationName());
            bookingHistoryResponse.setCarImageUrl(a.getVehicleWithBooking().getCarImageUrl());
            bookingHistoryResponseList.add(bookingHistoryResponse);
        });
        return bookingHistoryResponseList;
    }

}
