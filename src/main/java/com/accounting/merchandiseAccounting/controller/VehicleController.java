package com.accounting.merchandiseAccounting.controller;

import com.accounting.merchandiseAccounting.exceptions.ResourceNotFoundException;
import com.accounting.merchandiseAccounting.model.Vehicle;
import com.accounting.merchandiseAccounting.service.VehicleService;
import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("find/")
    public ResponseEntity getVehicleByName(@RequestParam Optional<String> name){
        List<Vehicle> vehicleList = vehicleService.findVehicleByVehicleName(name.orElse("_"));
        return new ResponseEntity(vehicleList,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllVehicle(){
        List<Vehicle> vehicleList = vehicleService.getAllVehicle();
       try {
        return new ResponseEntity<>(vehicleList, HttpStatus.OK);
       }catch (PropertyAccessException propertyAccessException){
           return new ResponseEntity<PropertyAccessException>(propertyAccessException,HttpStatus.EXPECTATION_FAILED);
       }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable("id") long id) throws ResourceNotFoundException {
        Vehicle vehicle = vehicleService.findVehicleById(id);
        if(vehicle == null){
            throw new ResourceNotFoundException("Vehicle with id: " + " is not present");
        }
        return new ResponseEntity<>(vehicle,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveVehicle(@Validated @RequestBody Vehicle vehicle){
        System.out.println(vehicle);
        vehicleService.saveVehicle(vehicle);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable("id") long id) throws ResourceNotFoundException {
        int res = vehicleService.deleteVehicleById(id);
        if(res == 0){
            throw new ResourceNotFoundException("Vehicle with id: " + " is not present");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("available")
    public ResponseEntity getAllAvailableVehicle(){
        List<Vehicle> vehicleList = vehicleService.getAllAvailableVehicle();
        return new ResponseEntity(vehicleList,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity updateVehicle(@RequestBody Vehicle vehicle){
        vehicleService.updateVehicle(vehicle);
        return new ResponseEntity(HttpStatus.OK);
    }
}
