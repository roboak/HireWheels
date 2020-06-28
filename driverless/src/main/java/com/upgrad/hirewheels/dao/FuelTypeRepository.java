package com.upgrad.hirewheels.dao;

import com.upgrad.hirewheels.entities.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelTypeRepository extends JpaRepository<FuelType, Integer> {
}
