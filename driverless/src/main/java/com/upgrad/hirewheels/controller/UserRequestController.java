package com.upgrad.hirewheels.controller;

import com.upgrad.hirewheels.dto.AddVehicleDTO;
import com.upgrad.hirewheels.dto.OptVehicleDTO;
import com.upgrad.hirewheels.entities.Vehicle;
import com.upgrad.hirewheels.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserRequestController {

    @Autowired
    RequestService requestService;

    @PutMapping("/optVehicle/{vehicleId}")
    public ResponseEntity addVehicleRequest(@RequestBody OptVehicleDTO vehicle, @PathVariable int vehicleId) {
            requestService.userOptRequest(vehicle, vehicleId);
            return ResponseEntity.ok("Request successful. Kindly wait for Admin to approve.");
        }

    @PostMapping("/addVehicle/{userId}")
    public ResponseEntity addVehicleRequest(@RequestBody AddVehicleDTO vehicle, @PathVariable int userId){
        String userRole = vehicle.getUserRole();
        Vehicle returnedVehicle = requestService.addVehicleRequest(vehicle,userId);
        System.out.println(returnedVehicle.getVehicleId()+ "added vehicle");
        if(!userRole.equals("Admin")){
            return ResponseEntity.ok("Vehicle Added Successfully. Waiting for Admin to Approve.");
        } else {
            return  ResponseEntity.ok("Vehicle Added Successfully.");
        }
    }
    }


