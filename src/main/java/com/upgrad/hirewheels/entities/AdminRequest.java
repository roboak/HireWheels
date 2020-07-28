package com.upgrad.hirewheels.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="ADMINREQUEST")
public class AdminRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int requestId;
    String userComments;
    String adminComments;
    @ManyToOne(fetch = FetchType.EAGER,cascade
            = CascadeType.MERGE)
    @JsonBackReference
    @JoinColumn(name = "requestStatusId")
    RequestStatus requestStatus;
    @ManyToOne(fetch = FetchType.EAGER,cascade
            = CascadeType.MERGE)
    @JsonBackReference
    @JoinColumn(name = "userId")
    User user;
    @ManyToOne(fetch = FetchType.EAGER,cascade
            = CascadeType.MERGE)
    @JoinColumn(name = "activityId")
    @JsonBackReference
    Activity activity;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "vehicleId")
    @JsonBackReference
    Vehicle vehicle;
}
