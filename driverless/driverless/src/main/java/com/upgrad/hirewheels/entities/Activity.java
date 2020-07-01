package com.upgrad.hirewheels.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="ACTIVITY")
public class Activity {
    @Id
    int activityId;
    @Column( nullable = false, unique = true)
    String activityType;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "activity")
    @JsonManagedReference
    List<AdminRequest> adminRequestList;
}


