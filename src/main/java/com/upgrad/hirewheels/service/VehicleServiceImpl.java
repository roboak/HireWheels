package com.upgrad.hirewheels.service;

import com.upgrad.hirewheels.dao.*;
import com.upgrad.hirewheels.entities.Booking;
import com.upgrad.hirewheels.entities.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    VehicleDAO vehicleDAO;

    @Autowired
    VehicleCategoryDAO vehicleCategoryDAO;

    @Autowired
    BookingDAO bookingDAO;

    /**
     * Returns all the available vehicle in the requested Category for
     * booking with respect to Date, Location and Availability.
     * @param categoryName
     * @param pickUpDate
     * @param dropDate
     * @param locationId
     * @return
     */

    public List<Vehicle> getAvailableVehicles(String categoryName, Date pickUpDate,Date dropDate, int locationId) {

        List<Vehicle> returnedVehicleList = new ArrayList<>();

        /**
         * Fetch a list of all vehicles of given vehicle category which are available in the desired location
         * and with availability_status = 1
         */
        vehicleCategoryDAO.findByVehicleCategoryName(categoryName).getVehicleSubCategoriesList()
                .forEach(a -> a.getVehicleList()
                .forEach(b -> {
                    if(b.getLocationWithVehicle().getLocationId() == locationId && b.getAvailabilityStatus() == 1)
                        returnedVehicleList.add(b);
                }));

        /**
         *  Get a list of all the vehicle Ids which have booking during input booking slot.
         *  A vehicle is unavailable for booking if any of the following three scenarios are met-
         *
         *  a. booking pick up date > Booked Vehicle's pickup date &&
         *  booking pick up date < Booked Vehicle's dropoff date
         *
         *  b. booking drop off date > Booked Vehicle's pickup date &&
         *  booking drop off date < Booked Vehicle's dropoff date
         *
         *  c. booking pickup date < Booked vehicle's pick up date &&
         *  booking drop off date > Booked vehicle's drop off date
         *
         * Apart from this, we also need to consider those vehicles as booked if booking pick or dropoff date
         * equals to either booked vehicle's pickup date or dropoff date.
         *
         */
        List<Integer> bookedVehicleIdList = new ArrayList<>();
        returnedVehicleList.forEach(a-> {
            List<Booking> bookedVehicleList = bookingDAO.findByVehicleWithBooking(a);
            bookedVehicleList.forEach(b ->{
                if ((pickUpDate.after(b.getPickUpDate()) && pickUpDate.before(b.getDropOffDate()))
                        || (dropDate.after(b.getPickUpDate()) && dropDate.before(b.getDropOffDate()))
                        || (pickUpDate.before(b.getPickUpDate()) && dropDate.after(b.getDropOffDate()))
                        || pickUpDate.equals(b.getDropOffDate())
                        || dropDate.equals(b.getPickUpDate())
                        || pickUpDate.equals(b.getPickUpDate())
                        || dropDate.equals(b.getDropOffDate())){
                    bookedVehicleIdList.add(b.getVehicleWithBooking().getVehicleId());
                }
            });
        });

        /**
         * Filter out those vehicles from the returnedVehicleList
         * which are already booked in the booking slot.
         */
        List<Vehicle> availableVehicles = new ArrayList<>();
        returnedVehicleList.forEach(a-> {
            if(!bookedVehicleIdList.contains(a.getVehicleId() )){
                availableVehicles.add(a);
            }
        });
       return availableVehicles;
    }


    /**
     * Returns all the vehicle registered on the application.
     * @param
     * @return
     */
    public List<Vehicle> fetchAllVehicles() {
        List<Vehicle> returnedVehicleList = vehicleDAO.findAll();
        return returnedVehicleList;
    }

}

