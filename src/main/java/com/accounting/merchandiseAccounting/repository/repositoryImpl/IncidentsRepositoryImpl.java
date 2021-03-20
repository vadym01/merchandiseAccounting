package com.accounting.merchandiseAccounting.repository.repositoryImpl;

import com.accounting.merchandiseAccounting.exceptions.customExceptionHandler.IdNotFoundException;
import com.accounting.merchandiseAccounting.model.Employee;
import com.accounting.merchandiseAccounting.model.Vehicle;
import com.accounting.merchandiseAccounting.model.Incidents;
import com.accounting.merchandiseAccounting.repository.IncidentsRepository;
import com.accounting.merchandiseAccounting.service.EmployeeService;
import com.accounting.merchandiseAccounting.service.VehicleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
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
    private VehicleService vehicleService;
    @Autowired
    private SessionFactory sessionFactory;
    private Session session;

    @PostConstruct
    public void init() {
        session = sessionFactory.openSession();
    }

    @Override
    public Incidents findIncidentById(long id) {
        try {
            Query query = session.createNamedQuery("findIncidentById").setParameter("id", id);
            Incidents incidents = (Incidents) query.list().get(0);
            return incidents;
        } catch (Exception e) {
            throw new IdNotFoundException("Incident with id: " + id + " are not present");
        }
    }

    @Override
    public List<Incidents> findAllIncidents() {
        List<Incidents> incidents = new ArrayList<>();
        try {
            Query query = session.getNamedQuery("findAllIncidents");
            incidents = query.list();
        } catch (Exception e) {
            throw new HibernateException("Database connection error");
        }
        return incidents;
    }

    @Override
    public int deleteIncidentById(long id) {
        try {
            Query query = session.getNamedQuery("deleteIncidentById").setParameter("id", id);
            session.beginTransaction();
            int num = query.executeUpdate();
            session.getTransaction().commit();
            return num;
        } catch (HibernateException hibernateException) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeException) {
                runtimeException.printStackTrace();
            }
            hibernateException.printStackTrace();
            return 0;
        }
    }

    @Override
    public Incidents saveNewIncident(Incidents incidents) {
        try {
            session.getTransaction().begin();
            Incidents incidents1 = (Incidents) session.merge(incidents);
            session.getTransaction().commit();
            return incidents1;
        } catch (HibernateException hibernateException) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeException) {
                runtimeException.printStackTrace();
            }
            hibernateException.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Incidents> findIncidentsForVehicle() {
        try {
            Query query = session.getNamedQuery("findIncidentsForVehicle");
            List<Incidents> incidentsList = query.getResultList();
            return incidentsList;
        }catch (Exception e){
            e.printStackTrace();
            throw new HibernateException("Database connection error");
        }
    }

    @Override
    public List<Incidents> findIncidentsForEmployee() {
        try {
            Query query = session.getNamedQuery("findIncidentsForEmployee");
            List<Incidents> incidentsList = query.getResultList();
            return incidentsList;
        }catch (Exception e){
            e.printStackTrace();
            throw new HibernateException("Database connection error");
        }
    }
}
