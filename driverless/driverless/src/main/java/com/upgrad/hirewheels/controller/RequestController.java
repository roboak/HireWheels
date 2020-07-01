package com.upgrad.hirewheels.controller;

import com.upgrad.hirewheels.dto.VehicleDTO;
import com.upgrad.hirewheels.dto.AdminRequestDTO;
import com.upgrad.hirewheels.exceptions.GlobalExceptionHandler;
import com.upgrad.hirewheels.responsemodel.SuccessResponse;
import com.upgrad.hirewheels.service.RequestService;
import com.upgrad.hirewheels.validator.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    RequestService requestService;

    @Autowired
    RequestValidator requestValidator;

    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    @PutMapping("/{vehicleId}")
    public ResponseEntity changeVehicleAvailability(@RequestBody AdminRequestDTO vehicle, @PathVariable int vehicleId) {
        ResponseEntity responseEntity = null;
        try{
            requestValidator.validateChangeVehicleAvailability(vehicle, vehicleId);
            requestService.changeAvailabilityRequest(vehicle, vehicleId);
            SuccessResponse response = new SuccessResponse(new Date(), "Request successful. Kindly wait for Admin to approve.", 200);
            responseEntity =  new ResponseEntity(response, HttpStatus.OK);
        } catch (GlobalExceptionHandler e){
            logger.error(e.getMessage());
        }
            return responseEntity;
        }

    @PostMapping("/vehicle")
    public ResponseEntity addVehicleRequest(@RequestBody VehicleDTO vehicleDTO){
        ResponseEntity responseEntity = null;
        try{
            requestValidator.validateAddVehicleRequest(vehicleDTO);
            requestService.addVehicleRequest(vehicleDTO);
            String message = null;
            if (vehicleDTO.getUserId() != 1){
                message = "Vehicle Added Successfully. Waiting for Admin to Approve.";
            } else {
                message = "Vehicle Added Successfully.";
            }
            SuccessResponse response = new SuccessResponse(new Date(), message, 200);
            responseEntity =  new ResponseEntity(response, HttpStatus.OK);
        } catch (GlobalExceptionHandler e){
            logger.error(e.getMessage());
        }
        return responseEntity;
    }
    }


