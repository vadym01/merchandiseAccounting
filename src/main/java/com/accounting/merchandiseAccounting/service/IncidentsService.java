package com.accounting.merchandiseAccounting.service;


import com.accounting.merchandiseAccounting.model.Incidents;

import java.util.List;

public interface IncidentsService {
//    void registerNewIncident(Incidents incidents, long employeeId, long vehicleId);

    Incidents findIncidentById(long id);

    int deleteIncidentById(long id);

    List<Incidents> findAllIncidents();

    Incidents saveNewIncident(Incidents incidents);

    List<Incidents> findIncidentsForVehicle();

    List<Incidents> findIncidentsForEmployee();

}
