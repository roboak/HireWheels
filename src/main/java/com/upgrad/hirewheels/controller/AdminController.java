package com.upgrad.hirewheels.controller;


import com.upgrad.hirewheels.dto.VehicleDTO;
import com.upgrad.hirewheels.entities.Vehicle;
import com.upgrad.hirewheels.exceptions.APIException;
import com.upgrad.hirewheels.exceptions.VehicleNotFoundException;
import com.upgrad.hirewheels.exceptions.VehicleNumberNotUniqueException;
import com.upgrad.hirewheels.responsemodel.CustomResponse;
import com.upgrad.hirewheels.service.AdminService;
import com.upgrad.hirewheels.utils.DTOEntityConverter;
import com.upgrad.hirewheels.validator.AdminRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    AdminRequestValidator adminRequestValidator;

    @Autowired
    DTOEntityConverter dtoEntityConverter;

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @PutMapping("vehicles/{vehicleId}")
    public ResponseEntity changeVehicleAvailability(@RequestBody VehicleDTO vehicleDTO, @PathVariable int vehicleId) throws APIException, VehicleNotFoundException {
        ResponseEntity responseEntity = null;
        int availability_status = vehicleDTO.getAvailability_status();
        adminRequestValidator.validateChangeVehicleAvailability(availability_status);
        Vehicle vehicle = adminService.changeAvailabilityRequest(vehicleId, availability_status);
        CustomResponse response = new CustomResponse(new Date(), "Activity performed successfully", 200);
        responseEntity =  new ResponseEntity(response, HttpStatus.OK);
        return responseEntity;
        }

    @PostMapping("/vehicles")
    public ResponseEntity addVehicleRequest(@RequestBody VehicleDTO vehicleDTO) throws APIException, VehicleNumberNotUniqueException {
        ResponseEntity responseEntity = null;
        adminRequestValidator.validateAddVehicleRequest(vehicleDTO);
        Vehicle vehicle = dtoEntityConverter.convertToVehicleEntity(vehicleDTO);
        adminService.enterVehicleDetails(vehicle);
        CustomResponse response = new CustomResponse(new Date(), "Vehicle Added Successfully", 200);
        responseEntity =  new ResponseEntity(response, HttpStatus.OK);
        return responseEntity;
    }
    }


