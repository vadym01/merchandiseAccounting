package com.accounting.merchandiseAccounting.service;


import com.accounting.merchandiseAccounting.model.Incident;

import java.util.List;

public interface IncidentService {
//    void registerNewIncident(Incidents incidents, long employeeId, long vehicleId);

    Incident findIncidentById(long id);

    int deleteIncidentById(long id);

    List<Incident> findAllIncidents();

    Incident saveNewIncident(Incident incident);

    List<Incident> findIncidentsForVehicle();

    List<Incident> findIncidentsForEmployee();

}
