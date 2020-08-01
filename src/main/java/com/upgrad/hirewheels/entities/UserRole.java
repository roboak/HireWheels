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
    @Column(unique = true)
    String roleName;

    public UserRole(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public UserRole() {
    }

//    @OneToMany(fetch = FetchType.LAZY)
//    @JsonManagedReference
//    List<User> userList;
}
