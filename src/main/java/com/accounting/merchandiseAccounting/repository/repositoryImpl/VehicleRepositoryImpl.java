package com.accounting.merchandiseAccounting.repository.repositoryImpl;

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
    @Transactional
    public void saveVehicle(Vehicle vehicle) {
        try{
            session.merge(vehicle);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Vehicle findVehicleById(long id) {
        try {
            Query query = session.getNamedQuery("findVehicleById").setParameter("id",id);
            Vehicle vehicle = (Vehicle)query.getSingleResult();
            return vehicle;
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @Transactional
    @Override
    public int deleteVehicleById(long id) {
        try {
            Query query = session.getNamedQuery("deleteVehicleById").setParameter("id",id);
            Transaction transaction = session.beginTransaction();
            int num = query.executeUpdate();
            transaction.commit();
            return num;
        }catch (Exception e){
            logger.error(e.getMessage());
            return 0;
        }
    }

    @Transactional
    @Override
        public List<Vehicle> findVehicleByVehicleName(String name){
        try{
            Query query = session.getNamedQuery("findVehicleByVehicleName")
                    .setParameter("vehicleName", '%' + name + '%');
            List<Vehicle> vehicleList =query.list();
            return vehicleList;
        }catch (Exception e){
        logger.error(e.getMessage());
        return null;
        }
    }

    @Transactional
    @Override
    public void updateAvailableStatusById(long id) {
        session.getTransaction().begin();
       Vehicle vehicle = session.find(Vehicle.class,id);
       vehicle.setWorkable(!vehicle.isWorkable());
       session.getTransaction().commit();
    }

    @Transactional
    @Override
    public List<Vehicle> getAllVehicle() {
        List<Vehicle> vehicleList = new ArrayList<>();
        try {
            Query query = session.getNamedQuery("getAllVehicles");
            vehicleList = query.list();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return vehicleList;
    }

    @Transactional
    @Override
    public List<Vehicle> getAllAvailableVehicle() {
        try{
            Query query = session.getNamedQuery("getAllAvailableVehicle");
            List<Vehicle> vehicleList = query.list();
            return vehicleList;
        }catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Transactional
    @Override
    public void updateVehicle(Vehicle vehicle) {
        try {
            session.getTransaction().begin();
            session.merge(vehicle);
            session.getTransaction().commit();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }


}
