package com.upgrad.hirewheels.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="LOCATION")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int locationId;
    @Column( nullable = false)
    String locationName;
    @Column( nullable = false)
    String address;
    @Column( nullable = false)
    int pincode;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "locationWithVehicle",cascade
            = CascadeType.ALL)
    List<Vehicle> vehicles;
    @ManyToOne(fetch = FetchType.LAZY,cascade
            = CascadeType.MERGE)
    City city;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "locationWithBooking",cascade
            = CascadeType.MERGE)
    List<Booking> bookingsList;
}
