package com.upgrad.hirewheels.dao;

import com.upgrad.hirewheels.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityDAO extends JpaRepository<Activity, Integer> {
}
