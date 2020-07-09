package com.upgrad.hirewheels.controller;


import com.upgrad.hirewheels.dto.AdminActivityDTO;
import com.upgrad.hirewheels.exceptions.advice.GlobalExceptionHandler;
import com.upgrad.hirewheels.responsemodel.AdminRequestResponse;
import com.upgrad.hirewheels.responsemodel.CustomResponse;
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

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    AdminValidator adminValidator;

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);


    @GetMapping("requests")
    public ResponseEntity getAllApprovals(@RequestParam("statusId") int statusId){
        ResponseEntity responseEntity = null;
        try {
            adminValidator.validateGetAllApprovals(statusId);
            List<AdminRequestResponse> adminRequestList = adminService.getAllAdminRequest(statusId);
            responseEntity = ResponseEntity.ok(adminRequestList);
        } catch (GlobalExceptionHandler e) {
            logger.error(e.getMessage());
        }
        return responseEntity;
    }

    @PutMapping("/requests/{requestId}")
    public ResponseEntity updateVehicle(@RequestBody AdminActivityDTO adminActivityDTO, @PathVariable int requestId) {
        ResponseEntity responseEntity = null;
        try {
            adminValidator.validateUpdateVehicleRequest(adminActivityDTO, requestId);
            adminService.updateRequest(adminActivityDTO, requestId);
            CustomResponse response = new CustomResponse(new Date(), "Request Updated Successfully.",200);
            responseEntity =  new ResponseEntity(response, HttpStatus.OK);
        } catch (GlobalExceptionHandler e){
            logger.error(e.getMessage());
        }
    return responseEntity;
    }
}
