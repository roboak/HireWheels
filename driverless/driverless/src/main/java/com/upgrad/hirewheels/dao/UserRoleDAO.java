package com.upgrad.hirewheels.dao;

import com.upgrad.hirewheels.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDAO extends JpaRepository<UserRole, Integer> {
    UserRole findByRoleId(int roleId);
}
