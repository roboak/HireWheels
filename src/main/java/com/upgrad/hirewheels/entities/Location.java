package com.upgrad.hirewheels.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="LOCATION")
public class Location {
    @Id
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



    public Location(int locationId, String locationName, String address, int pincode, City city) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.address = address;
        this.pincode = pincode;
        this.city = city;
    }

    public Location(){

    }
}
