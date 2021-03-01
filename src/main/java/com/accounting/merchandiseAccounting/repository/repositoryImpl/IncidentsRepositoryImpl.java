package com.accounting.merchandiseAccounting.repository.repositoryImpl;

import com.accounting.merchandiseAccounting.model.Employee;
import com.accounting.merchandiseAccounting.model.Equipment;
import com.accounting.merchandiseAccounting.model.Incidents;
import com.accounting.merchandiseAccounting.repository.IncidentsRepository;
import com.accounting.merchandiseAccounting.service.EmployeeService;
import com.accounting.merchandiseAccounting.service.EquipmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class IncidentsRepositoryImpl implements IncidentsRepository {

    private final static Logger logger = LogManager.getLogger(EmployeeRepositoryImpl.class);

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private SessionFactory sessionFactory;
    private Session session;

    @PostConstruct
    public void init() {
        session = sessionFactory.openSession();
    }

    @Transactional
    @Override
    public void registerNewIncident(Incidents incidents, long employeeId, long equipmentId) {
        try {
            Incidents customIncidents = incidents;
            Employee employee = employeeService.findOneById(employeeId);
            Equipment equipment = equipmentService.findEquipmentById(equipmentId);
            customIncidents.setEmployee(employee);
            customIncidents.setEquipment(equipment);
            session.save(incidents);
        }catch (Exception e){
            logger.info(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Incidents findIncidentById(long id) {
        try {
            Query query = session.createNamedQuery("findIncidentById").setParameter("id", id);
            Incidents incidents = (Incidents) query.list().get(0);
            return incidents;
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @Transactional
    @Override
    public List<Incidents> findAllIncidents() {
        List<Incidents> incidents = new ArrayList<>();
        try {
            Query query = session.getNamedQuery("findAllIncidents");
            incidents = query.list();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return incidents;
    }

    @Transactional
    @Override
    public int deleteIncidentById(long id) {
        try {
            Query query = session.getNamedQuery("deleteIncidentById").setParameter("id", id);
            Transaction transaction = session.beginTransaction();
            int num = query.executeUpdate();
            transaction.commit();
            return num;
        }catch (Exception e){
            logger.error(e.getMessage());
            return 0;
        }
    }
}
