package com.upgrad.hirewheels.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="VEHICLE")
public class Vehicle {
   @Id
   @GeneratedValue//(strategy = GenerationType.IDENTITY)
   int vehicleId;
   @Column( nullable = false)
   String vehicleModel;
   @Column( nullable = false, unique = true)
   String vehicleNumber;
   @Column( nullable = false)
   String color;
   @Column( nullable = false)
   String carImageUrl;
   @Column( nullable = false)
   int availabilityStatus;

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

   @OneToMany(fetch = FetchType.EAGER, mappedBy = "vehicleWithBooking",cascade
           = CascadeType.MERGE)
   @JsonManagedReference
   List<Booking> bookingsList;

   public Vehicle(String vehicleModel, String vehicleNumber, String color, String carImageUrl, int availabilityStatus, VehicleSubCategory vehicleSubCategory, FuelType fuelType, Location locationWithVehicle) {
      this.vehicleModel = vehicleModel;
      this.vehicleNumber = vehicleNumber;
      this.color = color;
      this.carImageUrl = carImageUrl;
      this.availabilityStatus = availabilityStatus;
      this.vehicleSubCategory = vehicleSubCategory;
      this.fuelType = fuelType;
      this.locationWithVehicle = locationWithVehicle;
   }

   public Vehicle() {
   }
}
