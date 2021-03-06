package com.accounting.merchandiseAccounting.controller;

import com.accounting.merchandiseAccounting.exceptions.ResourceNotFoundException;
import com.accounting.merchandiseAccounting.model.Equipment;
import com.accounting.merchandiseAccounting.service.EquipmentService;
import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping
    public ResponseEntity<?> getAllEquipment(){
        List<Equipment> equipmentList = equipmentService.getAllEquipment();
       try {
        return new ResponseEntity<>(equipmentList, HttpStatus.OK);
       }catch (PropertyAccessException propertyAccessException){
           return new ResponseEntity<PropertyAccessException>(propertyAccessException,HttpStatus.EXPECTATION_FAILED);
       }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getEquipmentById(@PathVariable("id") long id) throws ResourceNotFoundException {
        Equipment equipment = equipmentService.findEquipmentById(id);
        if(equipment == null){
            throw new ResourceNotFoundException("Equipment with id: " + " is not present");
        }
        return new ResponseEntity<>(equipment,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveEquipment(@Validated @RequestBody Equipment equipment){
        equipmentService.saveEquipment(equipment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteEquipment(@PathVariable("id") long id) throws ResourceNotFoundException {
        int res = equipmentService.deleteEquipmentById(id);
        if(res == 0){
            throw new ResourceNotFoundException("Equipment with id: " + " is not present");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity changeEquipmentAvailableStatus(@PathVariable("id") long id){
        equipmentService.updateAvailableStatusById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("available")
    public ResponseEntity getAllAvailableEquipment(){
        List<Equipment> equipmentList = equipmentService.getAllAvailableEquipment();
        return new ResponseEntity(equipmentList,HttpStatus.OK);
    }
}
