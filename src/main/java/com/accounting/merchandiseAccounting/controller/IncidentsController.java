package com.accounting.merchandiseAccounting.controller;

import com.accounting.merchandiseAccounting.model.Incidents;
import com.accounting.merchandiseAccounting.service.IncidentsService;
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
@RequestMapping("/incidents")
public class IncidentsController {

    @Autowired
    private IncidentsService incidentsService;

    @Autowired
    private MapValidationService mapValidationService;

    @GetMapping("{id}")
    public ResponseEntity<Incidents> findIncidentById(@PathVariable("id") long id) {
        Incidents incidents = incidentsService.findIncidentById(id);
        return new ResponseEntity<Incidents>(incidents, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Incidents>> getAllIncidents() {
        List<Incidents> incidentsList = incidentsService.findAllIncidents();
        return new ResponseEntity<>(incidentsList, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity registerNewIncidentForVehicle(@Validated @RequestBody Incidents incidents, BindingResult bindingResult) {
        ResponseEntity<?> responseEntity = mapValidationService.mapValidationService(bindingResult);
        Incidents vehicleIncident = incidentsService.saveNewIncident(incidents);
        return new ResponseEntity(vehicleIncident, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteIncidentById(@PathVariable("id") long id){
        int response = incidentsService.deleteIncidentById(id);
//        if (response == 0) {
//            throw new I("Incident with id: " + id + " not found");
//        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("vehicle")
    public ResponseEntity findIncidentsForVehicle() {
        List<Incidents> incidentsList = incidentsService.findIncidentsForVehicle();
        return new ResponseEntity(incidentsList, HttpStatus.OK);
    }

    @GetMapping("employee")
    public ResponseEntity findIncidentsForEmployee() {
        List<Incidents> incidentsList = incidentsService.findIncidentsForEmployee();
        return new ResponseEntity(incidentsList, HttpStatus.OK);
    }
}
