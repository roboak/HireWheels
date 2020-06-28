package com.upgrad.hirewheels.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="VEHICLESUBCATEGORY")
public class VehicleSubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int vehicleSubCategoryId;
    String vehicleSubCategoryName;
    int pricePerHour;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicleSubCategory", cascade
            = CascadeType.ALL)
    List<Vehicle> vehicle;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicleCategoryId")
    VehicleCategory vehicleCategory;
}
