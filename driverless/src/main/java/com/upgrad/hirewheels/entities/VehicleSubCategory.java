package com.upgrad.hirewheels.entities;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="VEHICLESUBCATEGORY")
public class VehicleSubCategory {
    @Id
    int vehicleSubCategoryId;
    @Column( nullable = false, unique = true)
    String vehicleSubCategoryName;
    @Column( nullable = false)
    int pricePerHour;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicleSubCategory", cascade
            = CascadeType.ALL)
    List<Vehicle> vehicleList;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicleCategoryId")
    VehicleCategory vehicleCategory;
}
