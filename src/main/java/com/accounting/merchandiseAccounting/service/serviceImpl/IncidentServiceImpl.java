package com.accounting.merchandiseAccounting.service.serviceImpl;

import com.accounting.merchandiseAccounting.model.Employee;
import com.accounting.merchandiseAccounting.model.Incident;
import com.accounting.merchandiseAccounting.repository.IncidentRepository;
import com.accounting.merchandiseAccounting.service.EmployeeService;
import com.accounting.merchandiseAccounting.service.IncidentService;
import com.accounting.merchandiseAccounting.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentServiceImpl implements IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private VehicleService vehicleService;

    @Override
    public Incident findIncidentById(long id) {
        Incident incident = incidentRepository.findIncidentById(id);
        return incident;
    }

    @Override
    public int deleteIncidentById(long id) {
        int result = incidentRepository.deleteIncidentById(id);
        return result;
    }

    @Override
    public List<Incident> findAllIncidents() {
        List<Incident> incidentList = incidentRepository.findAllIncidents();
        return incidentList;
    }

    @Override
    public Incident saveNewIncident(Incident incident) {
        Incident vehicleIncident = incidentRepository.saveNewIncident(incident);
        if(incident.getEmployee() != null){
            employeeService.changeAvailableStatusForEmployee(incident.getEmployee().getId());
        }else {
            vehicleService.changeAvailableStatusForVehicle(incident.getVehicle().getId());
        }
        return vehicleIncident;
    }

    @Override
    public List<Incident> findIncidentsForVehicle() {
        List<Incident> incidentList = incidentRepository.findIncidentsForVehicle();
        return incidentList;
    }

    @Override
    public List<Incident> findIncidentsForEmployee() {
        List<Incident> incidentList = incidentRepository.findIncidentsForEmployee();
        return incidentList;
    }

    @Override
    public void changeIncidentStatus(long incidentId) {
        Incident incident = findIncidentById(incidentId);
        incident.setResolved(!incident.isResolved());
        if(incident.getEmployee() != null){
        employeeService.changeAvailableStatusForEmployee(incident.getEmployee().getId());
        }else {
            vehicleService.changeAvailableStatusForVehicle(incident.getVehicle().getId());
        }
        incidentRepository.updateIncident(incident);
    }
}
