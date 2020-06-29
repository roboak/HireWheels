package com.upgrad.hirewheels.controller;

import com.upgrad.hirewheels.dto.AddVehicleDTO;
import com.upgrad.hirewheels.dto.OptVehicleDTO;
import com.upgrad.hirewheels.entities.Vehicle;
import com.upgrad.hirewheels.exceptions.GlobalExceptionHandler;
import com.upgrad.hirewheels.responsemodel.SuccessResponse;
import com.upgrad.hirewheels.service.RequestService;
import com.upgrad.hirewheels.validator.UserRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserRequestController {

    @Autowired
    RequestService requestService;

    @Autowired
    UserRequestValidator userRequestValidator;

    private static final Logger logger = LoggerFactory.getLogger(UserRequestController.class);

    @PutMapping("/optVehicle/{vehicleId}")
    public ResponseEntity optVehicleRequest(@RequestBody OptVehicleDTO vehicle, @PathVariable int vehicleId) {
        ResponseEntity responseEntity = null;
        try{
            userRequestValidator.validateOptVehicleRequest(vehicle, vehicleId);
            requestService.userOptRequest(vehicle, vehicleId);
            SuccessResponse response = new SuccessResponse(new Date(), "Request successful. Kindly wait for Admin to approve.",
                    "/user/addVehicleOptRequest", 200);
            responseEntity =  new ResponseEntity(response, HttpStatus.OK);
        } catch (GlobalExceptionHandler e){
            logger.error(e.getMessage());
        }
            return responseEntity;
        }

    @PostMapping("/addVehicle/{userId}")
    public ResponseEntity addVehicleRequest(@RequestBody AddVehicleDTO vehicle, @PathVariable int userId){
        String userRole = vehicle.getUserRole();
        ResponseEntity responseEntity = null;
        try{
            userRequestValidator.validateAddVehicleRequest(vehicle,userId);
            requestService.addVehicleRequest(vehicle,userId);
            String message = null;
            if (!userRole.equals("Admin")){
                message = "Vehicle Added Successfully. Waiting for Admin to Approve.";
            } else {
                message = "Vehicle Added Successfully.";
            }
            SuccessResponse response = new SuccessResponse(new Date(), message,
                    "/user/addVehicle", 200);
            responseEntity =  new ResponseEntity(response, HttpStatus.OK);
        } catch (GlobalExceptionHandler e){
            logger.error(e.getMessage());
        }
        return responseEntity;
    }
    }


