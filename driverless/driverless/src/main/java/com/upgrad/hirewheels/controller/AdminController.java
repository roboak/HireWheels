package com.upgrad.hirewheels.controller;


import com.upgrad.hirewheels.dto.OptVehicleDTO;
import com.upgrad.hirewheels.exceptions.GlobalExceptionHandler;
import com.upgrad.hirewheels.responsemodel.AvailableRequestResponse;
import com.upgrad.hirewheels.responsemodel.SuccessResponse;
import com.upgrad.hirewheels.service.AdminService;
import com.upgrad.hirewheels.validator.AdminValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    AdminValidator adminValidator;

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);


    @GetMapping("allApprovals/{requestId}")
    public ResponseEntity getAllApprovals(@PathVariable int requestId){
        ResponseEntity responseEntity = null;
        try {
            adminValidator.validateApprovals(requestId);
            List<AvailableRequestResponse> adminRequestList = adminService.getAllAdminRequest(requestId);
            responseEntity = ResponseEntity.ok(adminRequestList);
        } catch (GlobalExceptionHandler e) {
            logger.error(e.getMessage());
        }
        return responseEntity;
    }

    @PutMapping("/updateApproval/{vehicleId}")
    public ResponseEntity addVehicleRequest(@RequestBody OptVehicleDTO vehicle, @PathVariable int vehicleId) {
        ResponseEntity responseEntity = null;
        try {
            adminValidator.validateAddVehicleRequest(vehicle, vehicleId);
            adminService.updateRequest(vehicle, vehicleId);
            SuccessResponse response = new SuccessResponse(new Date(), "Request Updated Success.",
                    "/admin/updateApproval", 200);
            responseEntity =  new ResponseEntity(response, HttpStatus.OK);
        } catch (GlobalExceptionHandler e){
            logger.error(e.getMessage());
        }
    return responseEntity;
    }
}
