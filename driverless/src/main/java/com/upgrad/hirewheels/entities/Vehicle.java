package com.upgrad.hirewheels.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="VEHICLE")
public class Vehicle {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   int vehicleId;
   String vehicleModel;
   String vehicleNumber;
   String color;
   String carImageUrl;
   @ManyToOne(fetch = FetchType.LAZY,cascade
           = CascadeType.MERGE)
   @JoinColumn(name = "vehicleSubCategoryId")
   @JsonBackReference
   VehicleSubCategory vehicleSubCategory;
   @ManyToOne(fetch = FetchType.EAGER,cascade
           = CascadeType.MERGE)
   @JoinColumn(name = "fuelTypeId")
   @JsonBackReference
   FuelType fuelType;
   @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
   @JsonBackReference
   @JoinColumn(name = "locationId")
   Location locationWithVehicle;
   @ManyToOne(fetch = FetchType.EAGER,cascade
           = CascadeType.MERGE)
   @JsonBackReference
   @JoinColumn(name = "userId")
   Users users;
   @OneToMany(fetch = FetchType.EAGER, mappedBy = "vehicleWithBooking",cascade
           = CascadeType.MERGE)
   @JsonManagedReference
   List<Booking> bookingsList;
   @OneToOne(mappedBy = "vehicle")
   @JsonManagedReference
   AdminRequest adminRequest;
}
