package com.accounting.merchandiseAccounting.controller;

import com.accounting.merchandiseAccounting.exceptions.ResourceNotFoundException;
import com.accounting.merchandiseAccounting.model.Incidents;
import com.accounting.merchandiseAccounting.service.IncidentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/incidents")
public class IncidentsController {

    @Autowired
    private IncidentsService incidentsService;

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

    @PostMapping("{employeeId}/{vehicleId}")
    public ResponseEntity registerNewIncident(@RequestBody Incidents incidents,
                                              @PathVariable("employeeId") long employeeId,
                                              @PathVariable("vehicleId") long vehicleId) {
        incidentsService.registerNewIncident(incidents, employeeId, vehicleId);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/vehicle/{vehicleId}")
    public ResponseEntity registerNewIncidentForVehicle(@RequestBody Incidents incidents,
                                                        @PathVariable("vehicleId") long vehicleId) {
        Incidents vehicleIncident = incidentsService.registerNewIncidentForVehicle(incidents, vehicleId);
        return new ResponseEntity(vehicleIncident, HttpStatus.OK);
    }


    @PatchMapping("/vehicle/{vehicleId}")
    public ResponseEntity updateVehicle(@RequestBody Incidents incidents,
                                        @PathVariable("vehicleId") long vehicleId) {
        incidentsService.updateVehicleIncident(incidents);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/employee/{employeeId}")
    public ResponseEntity registerNewIncidentForEmployee(@RequestBody Incidents incidents,
                                                         @PathVariable("employeeId") long employeeId) {
        incidentsService.registerNewIncidentForEmployee(incidents, employeeId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteIncidentById(@PathVariable("id") long id) throws ResourceNotFoundException {
        int response = incidentsService.deleteIncidentById(id);
        if (response == 0) {
            throw new ResourceNotFoundException("Incident with id: " + id + " not found");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("vehicle")
    public ResponseEntity findIncidentsForvehicle() {
        List<Incidents> incidentsList = incidentsService.findIncidentsForVehicle();
        return new ResponseEntity(incidentsList, HttpStatus.OK);
    }

    @GetMapping("employee")
    public ResponseEntity findIncidentsForEmployee() {
        List<Incidents> incidentsList = incidentsService.findIncidentsForEmployee();
        return new ResponseEntity(incidentsList, HttpStatus.OK);
    }


}
