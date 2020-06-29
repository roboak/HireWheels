package com.upgrad.hirewheels.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "USERS")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int userId;
    @Column(nullable = false)
    String firstName;
    String lastName;
    @Column( nullable = false)
    String password;
    @Column( nullable = false, unique = true)
    String email;
    @Column( nullable = false, unique = true)
    long mobileNo;
    int walletMoney;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    UserRole userRole;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference
    List<Vehicle> vehiclesList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookingWithUser")
    @JsonManagedReference
    List<Booking> bookingList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference
    List<AdminRequest> adminRequestList;
}
