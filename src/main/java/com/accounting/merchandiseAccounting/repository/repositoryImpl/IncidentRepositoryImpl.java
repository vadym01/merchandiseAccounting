package com.accounting.merchandiseAccounting.repository.repositoryImpl;

import com.accounting.merchandiseAccounting.exceptions.customExceptionHandler.IdNotFoundException;
import com.accounting.merchandiseAccounting.model.Incident;
import com.accounting.merchandiseAccounting.repository.IncidentRepository;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class IncidentRepositoryImpl implements IncidentRepository {

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
    public Incident findIncidentById(long id) {
        try {
            Query query = session.createNamedQuery("findIncidentById").setParameter("id", id);
            Incident incident = (Incident) query.list().get(0);
            return incident;
        } catch (Exception e) {
            throw new IdNotFoundException("Incident with id: " + id + " are not present");
        }
    }

    @Override
    public List<Incident> findAllIncidents() {
        List<Incident> incidents = new ArrayList<>();
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
    public Incident saveNewIncident(Incident incident) {
        try {
            session.getTransaction().begin();
            Incident incident1 = (Incident) session.merge(incident);
            session.getTransaction().commit();
            return incident1;
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
    public List<Incident> findIncidentsForVehicle() {
        try {
            Query query = session.getNamedQuery("findIncidentsForVehicle");
            List<Incident> incidentList = query.getResultList();
            return incidentList;
        }catch (Exception e){
            e.printStackTrace();
            throw new HibernateException("Database connection error");
        }
    }

    @Override
    public List<Incident> findIncidentsForEmployee() {
        try {
            Query query = session.getNamedQuery("findIncidentsForEmployee");
            List<Incident> incidentList = query.getResultList();
            return incidentList;
        }catch (Exception e){
            e.printStackTrace();
            throw new HibernateException("Database connection error");
        }
    }
}
