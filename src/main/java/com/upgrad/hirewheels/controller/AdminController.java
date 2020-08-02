package com.upgrad.hirewheels.controller;


import com.upgrad.hirewheels.dto.VehicleDTO;
import com.upgrad.hirewheels.entities.Vehicle;
import com.upgrad.hirewheels.exceptions.*;
import com.upgrad.hirewheels.responsemodel.CustomResponse;
import com.upgrad.hirewheels.service.AdminService;
import com.upgrad.hirewheels.utils.Authorisation;
import com.upgrad.hirewheels.utils.DTOEntityConverter;
import com.upgrad.hirewheels.validator.AdminRequestValidator;
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

    @Autowired
    Authorisation authorisation;

    /**
     *
     * @param vehicleDTO
     * @param vehicleId
     * @param accessToken
     * @return
     * @throws APIException
     * @throws VehicleNotFoundException
     * @throws BadCredentialsException
     */
    @PutMapping("vehicles/{vehicleId}")
    public ResponseEntity changeVehicleAvailability(@RequestBody VehicleDTO vehicleDTO, @PathVariable int vehicleId, @RequestHeader(value = "X-ACCESS-TOKEN") String accessToken) throws APIException, VehicleNotFoundException, BadCredentialsException {
        authorisation.adminAuthorization(accessToken);
        ResponseEntity responseEntity = null;
        int availability_status = vehicleDTO.getAvailability_status();
        adminRequestValidator.validateChangeVehicleAvailability(availability_status);
        Vehicle vehicle = adminService.changeAvailabilityRequest(vehicleId, availability_status);
        CustomResponse response = new CustomResponse(new Date(), "Activity performed successfully", 200);
        responseEntity =  new ResponseEntity(response, HttpStatus.OK);
        return responseEntity;
        }

    /**
     *
     * @param vehicleDTO
     * @param accessToken
     * @return
     * @throws APIException
     * @throws VehicleNumberNotUniqueException
     * @throws InvalidLocationIdException
     * @throws BadCredentialsException
     */
    @PostMapping("/vehicles")
    public ResponseEntity addVehicleRequest(@RequestBody VehicleDTO vehicleDTO, @RequestHeader(value = "X-ACCESS-TOKEN") String accessToken) throws APIException, VehicleNumberNotUniqueException, InvalidLocationIdException, BadCredentialsException {
        authorisation.adminAuthorization(accessToken);
        ResponseEntity responseEntity = null;
        adminRequestValidator.validateAddVehicleRequest(vehicleDTO);
        Vehicle vehicle = dtoEntityConverter.convertToVehicleEntity(vehicleDTO);
        adminService.enterVehicleDetails(vehicle);
        CustomResponse response = new CustomResponse(new Date(), "Vehicle Added Successfully", 200);
        responseEntity =  new ResponseEntity(response, HttpStatus.OK);
        return responseEntity;
    }
    }


