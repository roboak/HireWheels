package com.upgrad.hirewheels.dao;

import com.upgrad.hirewheels.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
 List<Booking> findByPickUpDateGreaterThanEqualAndDropOffDateLessThanEqual(Date pickUpDate, Date dropOffDate);
}
