package com.accounting.merchandiseAccounting.service.serviceImpl;

import com.accounting.merchandiseAccounting.model.Incidents;
import com.accounting.merchandiseAccounting.repository.IncidentsRepository;
import com.accounting.merchandiseAccounting.service.IncidentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentsServiceImpl implements IncidentsService {

    @Autowired
    private IncidentsRepository incidentsRepository;

    @Override
    public void registerNewIncident(Incidents incidents, long employeeId, long equipmentId) {
        incidentsRepository.registerNewIncident(incidents, employeeId, equipmentId);
    }

    @Override
    public Incidents findIncidentById(long id) {
         Incidents incidents = incidentsRepository.findIncidentById(id);
         return incidents;
    }

    @Override
    public int deleteIncidentById(long id) {
        int result = incidentsRepository.deleteIncidentById(id);
        return result;
    }

    @Override
    public List<Incidents> findAllIncidents() {
        List<Incidents> incidentsList = incidentsRepository.findAllIncidents();
        return incidentsList;
    }
}
