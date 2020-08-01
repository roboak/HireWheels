package com.upgrad.hirewheels.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="VEHICLECATEGORY")
public class VehicleCategory {
    @Id
    int vehicleCategoryId;
    @Column( nullable = false , unique = true)
    String vehicleCategoryName;

    public VehicleCategory(int vehicleCategoryId, String vehicleCategoryName) {
        this.vehicleCategoryId = vehicleCategoryId;
        this.vehicleCategoryName = vehicleCategoryName;
    }

    public VehicleCategory() {
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicleCategory")
    List<VehicleSubCategory> vehicleSubCategoriesList;
}
