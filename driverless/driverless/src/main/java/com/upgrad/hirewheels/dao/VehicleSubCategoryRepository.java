package com.upgrad.hirewheels.dao;

import com.upgrad.hirewheels.entities.VehicleSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleSubCategoryRepository extends JpaRepository<VehicleSubCategory, Integer> {
}
