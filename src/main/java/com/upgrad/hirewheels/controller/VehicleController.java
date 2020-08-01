package com.upgrad.hirewheels.controller;


import com.upgrad.hirewheels.dto.VehicleDTO;
import com.upgrad.hirewheels.entities.Vehicle;
import com.upgrad.hirewheels.exceptions.APIException;
import com.upgrad.hirewheels.service.VehicleService;
import com.upgrad.hirewheels.utils.EntityDTOConverter;
import com.upgrad.hirewheels.validator.VehicleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    VehicleValidator vehicleValidator;

    @Autowired
    EntityDTOConverter entityDTOConverter;


    @GetMapping("/vehicles")
    public ResponseEntity getAvailableVehicles(@RequestParam("categoryName") String categoryName, @RequestParam("pickUpDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date pickupDate,
       @RequestParam("dropDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dropDate,
       @RequestParam("locationId") int locationId) throws APIException, ParseException {

        ResponseEntity responseEntity = null;
        List<Vehicle> vehiclesList;
        if(categoryName == null || categoryName.isEmpty() || dropDate == null || pickupDate == null ||locationId == 0){
            vehiclesList = vehicleService.fetchAllVehicles();
        }
        else {
            vehicleValidator.validategetAllVehicles(categoryName,pickupDate, dropDate, locationId);
            vehiclesList = vehicleService.getAvailableVehicles(categoryName,pickupDate,dropDate,locationId);
        }

        List<VehicleDTO> vehicleDTOList = entityDTOConverter.convertToVehicleDTO(vehiclesList);
        responseEntity =  ResponseEntity.ok(vehicleDTOList);

        return responseEntity;
    }


}
