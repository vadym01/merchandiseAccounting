package com.accounting.merchandiseAccounting.repository.repositoryImpl;


import com.accounting.merchandiseAccounting.exceptions.BadRequestExceptionHandler;
import com.accounting.merchandiseAccounting.exceptions.IdNotFoundException;
import com.accounting.merchandiseAccounting.model.Incident;
import com.accounting.merchandiseAccounting.repository.EmployeeRepository;
import com.accounting.merchandiseAccounting.repository.IncidentRepository;
import com.accounting.merchandiseAccounting.repository.VehicleRepository;
import com.accounting.merchandiseAccounting.service.EmployeeService;
import com.accounting.merchandiseAccounting.service.VehicleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.query.Query;

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
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    private Session session;

    @PostConstruct
    public void init() {
        session = sessionFactory.openSession();
    }

//    @Override
//    public Incident findIncidentById(long id) {
//        try {
//            Query query = session.createNamedQuery("findIncidentById").setParameter("id", id);
//            Incident incident = (Incident) query.getSingleResult();
//            return incident;
//        } catch (NoResultException e) {
//            throw new IdNotFoundException("No incident was found with id: " + id);
//        }catch (Exception e){
//            throw new BadRequestExceptionHandler(e.getMessage());
//        }
//    }

//    @Override
//    public List<Incident> findAllIncidents() {
//        try {
//            Query query = session.getNamedQuery("findAllIncidents");
//            List<Incident> incidents = query.list();
//            return incidents;
//        } catch (Exception e) {
//            throw new BadRequestExceptionHandler(e.getMessage());
//        }
//    }

//    @Override
//    public int deleteIncidentById(long id) {
//        try {
//            Query query = session.getNamedQuery("deleteIncidentById").setParameter("id", id);
//            session.beginTransaction();
//            int num = query.executeUpdate();
//            session.getTransaction().commit();
//            return num;
//        } catch (NoResultException e) {
//            try {
//                session.getTransaction().rollback();
//            } catch (RuntimeException runtimeException) {
//                throw new BadRequestExceptionHandler(runtimeException.getMessage());
//            }
//            e.printStackTrace();
//            throw new IdNotFoundException("No incident was found with id:"  + id);
//        } catch (Exception e){
//            throw new BadRequestExceptionHandler(e.getMessage());
//        }
//    }

//    @Override
//    public Incident saveNewIncident(Incident incident) {
//        try {
//            session.getTransaction().begin();
//            Incident incident1 = (Incident) session.merge(incident);
//            session.getTransaction().commit();
//            return incident1;
//        } catch (Exception e) {
//            try {
//                session.getTransaction().rollback();
//            } catch (RuntimeException runtimeException) {
//                throw new BadRequestExceptionHandler(runtimeException.getMessage());
//            }
//            throw new BadRequestExceptionHandler(e.getMessage());
//        }
//    }


    @Override
    public List<Incident> findIncidentsForVehicle() {
        try {
            Query query = session.getNamedQuery("findIncidentsForVehicle");
            List<Incident> incidentList = query.getResultList();
            return incidentList;
        } catch (Exception e) {
            throw new BadRequestExceptionHandler(e.getMessage());
        }
    }

    @Override
    public List<Incident> findIncidentsForEmployee() {
        try {
            Query query = session.getNamedQuery("findIncidentsForEmployee");
            List<Incident> incidentList = query.getResultList();
            return incidentList;
        } catch (Exception e) {
            throw new BadRequestExceptionHandler(e.getMessage());
        }
    }

//    @Override
//    public void updateIncident(Incident incident) {
//        try {
//            session.beginTransaction();
//            session.merge(incident);
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            try {
//                session.getTransaction().rollback();
//            } catch (RuntimeException runtimeException) {
//                throw new BadRequestExceptionHandler(runtimeException.getMessage());
//            }
//            throw new BadRequestExceptionHandler(e.getMessage());
//        }
//    }

}
