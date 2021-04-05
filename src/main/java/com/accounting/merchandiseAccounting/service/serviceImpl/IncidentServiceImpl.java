package com.accounting.merchandiseAccounting.service.serviceImpl;

import com.accounting.merchandiseAccounting.model.Incident;
import com.accounting.merchandiseAccounting.repository.IncidentRepository;
import com.accounting.merchandiseAccounting.repository.crudRepository.CrudProvider;
import com.accounting.merchandiseAccounting.service.EmployeeService;
import com.accounting.merchandiseAccounting.service.IncidentService;
import com.accounting.merchandiseAccounting.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IncidentServiceImpl implements IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private VehicleService vehicleService;

    private CrudProvider<Incident> crudProvider;

    @Autowired
    public void setCrudProvider(CrudProvider<Incident> crudProvider) {
        this.crudProvider = crudProvider;
        this.crudProvider.setClassInstance(Incident.class);
    }


    @Override
    public Incident findIncidentById(long id) {
        Incident incident = crudProvider.findOneById(id);
        return incident;
    }

    @Override
    public void deleteIncidentById(long id) {
        crudProvider.deleteById(id);
    }

    @Override
    public List<Incident> findAllIncidents() {
        List<Incident> incidentList = new ArrayList<>();
        incidentList.addAll(crudProvider.findAll());
        return incidentList;
    }

    @Override
    public Incident saveNewIncident(Incident incident) {
        System.out.println(incident.getId());
        Incident vehicleIncident = crudProvider.save(incident);
        if (incident.getEmployee() != null) {
            employeeService.changeAvailableStatusForEmployee(incident.getEmployee().getId());
        } else {
            vehicleService.changeAvailableStatusForVehicle(incident.getVehicle().getId());
        }
        return vehicleIncident;
    }

    @Override
    public List<Incident> findIncidentsForVehicle() {
        List<Incident> incidentList = new ArrayList<>();
        incidentList.addAll(incidentRepository.findIncidentsForVehicle());
        return incidentList;
    }

    @Override
    public List<Incident> findIncidentsForEmployee() {
        List<Incident> incidentList = new ArrayList<>();
        incidentList.addAll(incidentRepository.findIncidentsForEmployee());
        return incidentList;
    }

    @Override
    public void changeIncidentStatus(long incidentId) {
        Incident incident = findIncidentById(incidentId);
        incident.setResolved(!incident.isResolved());
        if (incident.getEmployee() != null) {
            employeeService.changeAvailableStatusForEmployee(incident.getEmployee().getId());
        } else {
            vehicleService.changeAvailableStatusForVehicle(incident.getVehicle().getId());
        }
        crudProvider.update(incident);
    }
}
