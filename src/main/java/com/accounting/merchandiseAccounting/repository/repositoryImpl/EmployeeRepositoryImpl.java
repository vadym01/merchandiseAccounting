package com.accounting.merchandiseAccounting.repository.repositoryImpl;

import com.accounting.merchandiseAccounting.exceptions.customExceptionHandler.IdNotFoundException;
import com.accounting.merchandiseAccounting.model.Employee;
import com.accounting.merchandiseAccounting.repository.EmployeeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.hibernate.query.Query;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final static Logger logger = LogManager.getLogger(EmployeeRepositoryImpl.class);

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private SessionFactory sessionFactory;
    private Session session;

    @PostConstruct
    public void init() {
        session = sessionFactory.openSession();
    }

    @Override
    public List<Employee> getAllEmployee() {
        List<Employee> employeeList;
        try {
            Query query = session.getNamedQuery("getAllEmployee");
            employeeList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new HibernateException("Database error");
        }
        return employeeList;
    }

    @Override
    public List<Employee> findEmployeeByName(String name) {
        List<Employee> employeeList;
        try {
            Query query = session.getNamedQuery("findEmployeeByName")
                    .setParameter("firstName", '%' + name + '%');
            employeeList = query.getResultList();
            return employeeList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HibernateException("Database error");
        }
    }

    @Override
    @Transactional
    public void saveEmployee(Employee employee) {
        try {
            session.save(employee);
        } catch (HibernateException hibernateException) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeException) {
                logger.error(runtimeException.getMessage());
            }
            hibernateException.printStackTrace();
        }
    }

    @Override
    public Employee getEmployeeById(long id) {
        Employee employee;
        try {
            Query query = session.getNamedQuery("getEmployeeById")
                    .setParameter("id", id);
            employee = (Employee) query.getSingleResult();
            return employee;
        } catch (Exception e) {
            throw new IdNotFoundException("Employee with id:" + id + " does not exist");
        }
    }

    @Override
    public List<Employee> getAllAvailableEmployees() {
        List<Employee> employeeList;
        try {
            Query query = session.getNamedQuery("getAllAvailableEmployees");
            employeeList = query.getResultList();
            return employeeList;
        } catch (Exception e) {
            throw new HibernateException("Hibernate exception");
        }
    }

    @Override
    public void updateEmployee(Employee employee) {
        try {
            session.beginTransaction();
            session.merge(employee);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeException) {
                logger.error(runtimeException.getMessage());
            }
            e.printStackTrace();
        }
    }
}


