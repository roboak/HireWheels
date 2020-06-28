package com.upgrad.hirewheels.dao;

import com.upgrad.hirewheels.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
}
