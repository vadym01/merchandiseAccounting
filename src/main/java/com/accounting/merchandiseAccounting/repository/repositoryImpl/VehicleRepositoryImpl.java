package com.accounting.merchandiseAccounting.repository.repositoryImpl;

import com.accounting.merchandiseAccounting.exceptions.customExceptionHandler.IdNotFoundException;
import com.accounting.merchandiseAccounting.model.Employee;
import com.accounting.merchandiseAccounting.model.Vehicle;
import com.accounting.merchandiseAccounting.repository.VehicleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleRepositoryImpl implements VehicleRepository {

    private final static Logger logger = LogManager.getLogger(EmployeeRepositoryImpl.class);

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private   SessionFactory sessionFactory;
    private Session session;

    @PostConstruct
    public void init() {
        session = sessionFactory.openSession();
    }

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        try{
            session.getTransaction().begin();
            session.merge(vehicle);
            session.getTransaction().commit();
            return vehicle;
        }catch (HibernateException hibernateException){
            try {
                session.getTransaction().rollback();
            }catch (RuntimeException runtimeException){
                runtimeException.printStackTrace();
            }
            hibernateException.printStackTrace();
            return null;
        }
    }

    @Override
    public Vehicle findVehicleById(long id) {
        try {
            Query query = session.getNamedQuery("findVehicleById").setParameter("id",id);
            Vehicle vehicle = (Vehicle)query.getSingleResult();
            return vehicle;
        }catch (Exception e){
            throw new IdNotFoundException("Vehicle with id: " + id + " are not present");
        }
    }

    @Override
    public int deleteVehicleById(long id) {
        try {
            Query query = session.getNamedQuery("deleteVehicleById").setParameter("id",id);
            Transaction transaction = session.beginTransaction();
            int num = query.executeUpdate();
            transaction.commit();
            return num;
        }catch (HibernateException hibernateException){
            try {
                session.getTransaction().rollback();
            }catch (RuntimeException runtimeException){
                runtimeException.printStackTrace();
            }
            hibernateException.printStackTrace();
            return 0;
        }
    }

    @Override
        public List<Vehicle> findVehicleByVehicleName(String name){
        try{
            Query query = session.getNamedQuery("findVehicleByVehicleName")
                    .setParameter("vehicleName", '%' + name + '%');
            List<Vehicle> vehicleList =query.list();
            return vehicleList;
        }catch (Exception e){
            e.printStackTrace();
        throw new HibernateException("Database connection error");
        }
    }

    @Override
    public List<Vehicle> getAllVehicle() {
        List<Vehicle> vehicleList = new ArrayList<>();
        try {
            Query query = session.getNamedQuery("getAllVehicles");
            vehicleList = query.list();
        }catch (Exception e){
            e.printStackTrace();
            throw new HibernateException("Database connection error");
        }
        return vehicleList;
    }

    @Override
    public List<Vehicle> getAllAvailableVehicle() {
        try{
            Query query = session.getNamedQuery("getAllAvailableVehicle");
            List<Vehicle> vehicleList = query.list();
            return vehicleList;
        }catch (Exception e) {
            throw new HibernateException("Database connection error");
        }
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        try {
            session.getTransaction().begin();
            session.merge(vehicle);
            session.getTransaction().commit();
        }catch (HibernateException hibernateException){
            try {
                session.getTransaction().rollback();
            }catch (RuntimeException runtimeException){
                runtimeException.printStackTrace();
            }
            hibernateException.printStackTrace();
        }
    }
}
