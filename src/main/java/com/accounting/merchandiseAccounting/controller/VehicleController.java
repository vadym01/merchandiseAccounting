package com.accounting.merchandiseAccounting.controller;

import com.accounting.merchandiseAccounting.exceptions.BadRequestExceptionHandler;
import com.accounting.merchandiseAccounting.model.Vehicle;
import com.accounting.merchandiseAccounting.service.VehicleService;
import com.accounting.merchandiseAccounting.validationService.MapValidationService;
import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private MapValidationService mapValidationService;

    @GetMapping("find/")
    public ResponseEntity getVehicleByName(@RequestParam Optional<String> name) {
        List<Vehicle> vehicleList = vehicleService.findVehicleByVehicleName(name.orElse("_"));
        return new ResponseEntity(vehicleList, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllVehicle() {
        List<Vehicle> vehicleList = vehicleService.getAllVehicles();
        try {
            return new ResponseEntity<>(vehicleList, HttpStatus.OK);
        } catch (PropertyAccessException propertyAccessException) {
            return new ResponseEntity<PropertyAccessException>(propertyAccessException, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable("id") long id) {
        Vehicle vehicle = vehicleService.findVehicleById(id);
        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveVehicle(@Validated @RequestBody Vehicle vehicle, BindingResult bindingResult) {
        ResponseEntity<?> responseEntity = mapValidationService.mapValidationService(bindingResult);
        if (responseEntity != null) return responseEntity;
        System.out.println(vehicle);
        Vehicle newVehicle = vehicleService.saveVehicle(vehicle);
        return new ResponseEntity<>(newVehicle, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable("id") long id) {
        vehicleService.deleteVehicleById(id);
//        if (res == 0) {
//            throw new BadRequestExceptionHandler("Vehicle with id: " + id + " is not present");
//        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("available")
    public ResponseEntity getAllAvailableVehicle() {
        List<Vehicle> vehicleList = vehicleService.getAllAvailableVehicles();
        return new ResponseEntity(vehicleList, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity updateVehicle(@Validated @RequestBody Vehicle vehicle, BindingResult bindingResult) {
        ResponseEntity<?> responseEntity = mapValidationService.mapValidationService(bindingResult);
        if (responseEntity != null) return responseEntity;
        vehicleService.updateVehicle(vehicle);
        return new ResponseEntity(HttpStatus.OK);
    }
}
