package com.accounting.merchandiseAccounting.repository.repositoryImpl;

import com.accounting.merchandiseAccounting.exceptions.BadRequestExceptionHandler;
import com.accounting.merchandiseAccounting.exceptions.IdNotFoundException;
import com.accounting.merchandiseAccounting.model.Vehicle;
import com.accounting.merchandiseAccounting.repository.VehicleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.query.Query;

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
        }catch (Exception e){
            try {
                session.getTransaction().rollback();
            }catch (RuntimeException runtimeException){
                throw new BadRequestExceptionHandler(runtimeException.getMessage());
            }
            throw new BadRequestExceptionHandler(e.getMessage());
        }
    }

    @Override
    public Vehicle findVehicleById(long id) {
        try {
            Query query = session.getNamedQuery("findVehicleById").setParameter("id",id);
            Vehicle vehicle = (Vehicle)query.getSingleResult();
            return vehicle;
        }catch (NoResultException e){
            throw new IdNotFoundException("No vehicle was found with id: " + id);
        }catch (Exception e){
            throw new BadRequestExceptionHandler(e.getMessage());
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
        }catch (NoResultException e) {
            throw new IdNotFoundException("No vehicle was found with id: " + id);
        }catch (Exception e){
            try {
                session.getTransaction().rollback();
            }catch (RuntimeException runtimeException){
                throw new BadRequestExceptionHandler(runtimeException.getMessage());
            }
            throw new BadRequestExceptionHandler(e.getMessage());
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
        throw new BadRequestExceptionHandler(e.getMessage());
        }
    }

    @Override
    public List<Vehicle> getAllVehicle() {
        try {
            Query query = session.getNamedQuery("getAllVehicles");
            List<Vehicle> vehicleList = query.list();
            return vehicleList;
        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestExceptionHandler(e.getMessage());
        }
    }

    @Override
    public List<Vehicle> getAllAvailableVehicle() {
        try{
            Query query = session.getNamedQuery("getAllAvailableVehicle");
            List<Vehicle> vehicleList = query.list();
            return vehicleList;
        }catch (Exception e) {
            throw new BadRequestExceptionHandler(e.getMessage());
        }
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        try {
            session.getTransaction().begin();
            session.merge(vehicle);
            session.getTransaction().commit();
        }catch (NoResultException e) {
            throw new IdNotFoundException("No vehicle was found with name: " + vehicle);
        }catch (Exception e){
            try {
                session.getTransaction().rollback();
            }catch (RuntimeException runtimeException){
                throw new BadRequestExceptionHandler(runtimeException.getMessage());
            }
            e.printStackTrace();
            throw new BadRequestExceptionHandler(e.getMessage());
        }
    }
}
