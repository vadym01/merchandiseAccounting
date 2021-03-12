package com.accounting.merchandiseAccounting.repository;

import com.accounting.merchandiseAccounting.model.Incidents;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentsRepository {
    void registerNewIncident(Incidents incidents, long employeeId, long vehicleId);

    Incidents findIncidentById(long id);

    int deleteIncidentById(long id);

    List<Incidents> findAllIncidents();

    Incidents registerNewIncidentForVehicle(Incidents incidents, long vehicleId);

    void registerNewIncidentForEmployee(Incidents incidents, long employeeId);

    List<Incidents> findIncidentsForVehicle();

    List<Incidents> findIncidentsForEmployee();

    void updateVehicleIncident(Incidents incidents);

}
