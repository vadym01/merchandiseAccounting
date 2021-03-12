package com.accounting.merchandiseAccounting.service;


import com.accounting.merchandiseAccounting.model.Incidents;
import com.accounting.merchandiseAccounting.model.Vehicle;

import java.util.List;

public interface IncidentsService {
    void registerNewIncident(Incidents incidents, long employeeId, long vehicleId);

    Incidents findIncidentById(long id);

    int deleteIncidentById(long id);

    List<Incidents> findAllIncidents();

    void registerNewIncidentForEmployee(Incidents incidents, long employeeId);

    Incidents registerNewIncidentForVehicle(Incidents incidents, long vehicleIncident);

    List<Incidents> findIncidentsForVehicle();

    List<Incidents> findIncidentsForEmployee();

    void updateVehicleIncident(Incidents incidents);

}
