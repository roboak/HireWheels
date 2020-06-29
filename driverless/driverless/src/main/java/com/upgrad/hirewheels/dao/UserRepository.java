package com.upgrad.hirewheels.dao;

import com.upgrad.hirewheels.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Users findByEmailAndPassword(String email, String password);
    Users findByEmailAndMobileNo(String email, long mobileNo);
    Users findByEmail(String email);
    Users findByMobileNo(long mobileNo);
}
