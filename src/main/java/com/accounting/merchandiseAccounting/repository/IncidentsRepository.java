package com.accounting.merchandiseAccounting.repository;

import com.accounting.merchandiseAccounting.model.Incidents;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentsRepository {
    void registerNewIncident(Incidents incidents, long employeeId,long equipmentId);
    Incidents findIncidentById(long id);
    int deleteIncidentById(long id);
    List<Incidents> findAllIncidents();
}
