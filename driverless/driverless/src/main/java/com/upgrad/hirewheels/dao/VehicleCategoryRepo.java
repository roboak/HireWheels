package com.upgrad.hirewheels.dao;

import com.upgrad.hirewheels.entities.VehicleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleCategoryRepo extends JpaRepository<VehicleCategory, Integer> {
VehicleCategory findByVehicleCategoryName(String category);
VehicleCategory findByVehicleCategoryId(int categoryId);
}
