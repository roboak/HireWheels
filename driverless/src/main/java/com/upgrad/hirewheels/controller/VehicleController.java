package com.upgrad.hirewheels.controller;


import com.upgrad.hirewheels.responsemodel.VehicleDetailResponse;
import com.upgrad.hirewheels.service.UserService;
import com.upgrad.hirewheels.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    UserService userService;

    @GetMapping("/allVehicles")
    public ResponseEntity getAvailableVehicles(@RequestParam("categoryName") String categoryName, @RequestParam("pickUpDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date pickupDate,
                                               @RequestParam("dropDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dropDate){
        List<VehicleDetailResponse> vehicleDetailResponse = vehicleService.getAvailableVehicles(categoryName, pickupDate, dropDate);
        return ResponseEntity.ok(vehicleDetailResponse);
    }

    @GetMapping("/allVehiclesByUser/{userId}")
    public ResponseEntity getVehicleByUserId(@PathVariable int userId){
        List<VehicleDetailResponse> vehicleDetailResponse = vehicleService.getAllVehicleByUserId(userId);
        return ResponseEntity.ok(vehicleDetailResponse);
    }

}
