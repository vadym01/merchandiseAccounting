package com.accounting.merchandiseAccounting.repository;

import com.accounting.merchandiseAccounting.model.Incident;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepository {
//    void registerNewIncident(Incidents incidents, long employeeId, long vehicleId);

//    Incident findIncidentById(long id);

//    int deleteIncidentById(long id);

//    List<Incident> findAllIncidents();

//    Incident saveNewIncident(Incident incident);

    List<Incident> findIncidentsForVehicle();

    List<Incident> findIncidentsForEmployee();

//    void updateIncident(Incident incident);

}
