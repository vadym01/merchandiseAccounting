package com.accounting.merchandiseAccounting.controller;

import com.accounting.merchandiseAccounting.exceptions.BadRequestExceptionHandler;
import com.accounting.merchandiseAccounting.exceptions.IdNotFoundException;
import com.accounting.merchandiseAccounting.model.Incident;
import com.accounting.merchandiseAccounting.service.IncidentService;
import com.accounting.merchandiseAccounting.validationService.MapValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/incident")
public class IncidentController {

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private MapValidationService mapValidationService;

    @GetMapping("{id}")
    public ResponseEntity<Incident> findIncidentById(@PathVariable("id") long id) {
        Incident incident = incidentService.findIncidentById(id);
        return new ResponseEntity<Incident>(incident, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Incident>> getAllIncident() {
        List<Incident> incidentList = incidentService.findAllIncidents();
        return new ResponseEntity<>(incidentList, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity registerNewIncident(@Validated @RequestBody Incident incident, BindingResult bindingResult) {
        ResponseEntity<?> responseEntity = mapValidationService.mapValidationService(bindingResult);
        if(responseEntity != null) return responseEntity;
        Incident vehicleIncident = incidentService.saveNewIncident(incident);
        return new ResponseEntity(vehicleIncident, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteIncidentById(@PathVariable("id") long id) {
         incidentService.deleteIncidentById(id);
//        if (response == 0) {
//            throw new IdNotFoundException("Incident with id: " + id + " not found");
//        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("vehicle")
    public ResponseEntity findIncidentForVehicle() {
        List<Incident> incidentList = incidentService.findIncidentsForVehicle();
        return new ResponseEntity(incidentList, HttpStatus.OK);
    }

    @GetMapping("employee")
    public ResponseEntity findIncidentForEmployee() {
        List<Incident> incidentList = incidentService.findIncidentsForEmployee();
        return new ResponseEntity(incidentList, HttpStatus.OK);
    }

    @PutMapping("status/{id}")
    public ResponseEntity changeIncidentStatus(@PathVariable("id") long id){
        incidentService.changeIncidentStatus(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
