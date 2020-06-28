package com.upgrad.hirewheels.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="USERROLE")
public class UserRole {
    @Id
    int roleId;
    String roleName;
    @OneToMany(fetch = FetchType.LAZY)
    @JsonManagedReference
    List<Users> usersList;
}
