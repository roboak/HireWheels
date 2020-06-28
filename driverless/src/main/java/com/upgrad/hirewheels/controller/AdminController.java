package com.upgrad.hirewheels.controller;


import com.upgrad.hirewheels.dto.OptVehicleDTO;
import com.upgrad.hirewheels.responsemodel.AvailableRequestResponse;
import com.upgrad.hirewheels.service.AdminService;
import com.upgrad.hirewheels.validator.AdminValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    AdminValidator adminValidator;


    @GetMapping("allApprovals/{requestId}")
    public ResponseEntity getVehicleByUserId(@PathVariable int requestId){
        List<AvailableRequestResponse> adminRequestList = adminService.getAllAdminRequest(requestId);
        return ResponseEntity.ok(adminRequestList);
    }

    @PutMapping("/updateApproval/{vehicleId}")
    public ResponseEntity addVehicleRequest(@RequestBody OptVehicleDTO vehicle, @PathVariable int vehicleId) {
        adminService.updateRequest(vehicle, vehicleId);
        return ResponseEntity.ok("Request successful.");
    }
}
