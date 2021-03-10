package com.accounting.merchandiseAccounting.repository.repositoryImpl;

import com.accounting.merchandiseAccounting.model.Equipment;
import com.accounting.merchandiseAccounting.repository.EquipmentRepository;
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
public class EquipmentRepositoryImpl implements EquipmentRepository {

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
    public void saveEquipment(Equipment equipment) {
        try{
            session.save(equipment);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Equipment findEquipmentById(long id) {
        try {
            Query query = session.getNamedQuery("findEquipmentById").setParameter("id",id);
            Equipment equipment = (Equipment)query.getResultList().get(0);
            return equipment;
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @Transactional
    @Override
    public int deleteEquipmentById(long id) {
        try {
            Query query = session.getNamedQuery("deleteEquipmentById").setParameter("id",id);
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
        public List<Equipment> findEquipmentByEquipmentName(String name){
        try{
            Query query = session.getNamedQuery("findEquipmentByEquipmentName")
                    .setParameter("equipmentName", '%' + name + '%');
            List<Equipment> equipmentList =query.list();
            return equipmentList;
        }catch (Exception e){
        logger.error(e.getMessage());
        return null;
        }
    }

    @Transactional
    @Override
    public void updateAvailableStatusById(long id) {
        session.getTransaction().begin();
       Equipment equipment = session.find(Equipment.class,id);
       equipment.setWorkable(!equipment.isWorkable());
       session.getTransaction().commit();
    }

    @Transactional
    @Override
    public List<Equipment> getAllEquipment() {
        List<Equipment> equipmentList = new ArrayList<>();
        try {
            Query query = session.getNamedQuery("getAllEquipment");
            equipmentList = query.list();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return equipmentList;
    }

    @Transactional
    @Override
    public List<Equipment> getAllAvailableEquipment() {
        try{
            Query query = session.getNamedQuery("getAllAvailableEquipment");
            List<Equipment> equipmentList = query.list();
            return equipmentList;
        }catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
