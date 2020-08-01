package com.upgrad.hirewheels.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="CITY")
public class City {
    @Id
    int cityId;
    @Column( nullable = false)
    String cityName;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city")
    List<Location> locations;

    public City() {
    }

    public City(int cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }
}

