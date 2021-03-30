package com.accounting.merchandiseAccounting.repository.repositoryImpl;

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

        try {
            Query query = session.getNamedQuery("getAllEmployee");
            List<Employee> employeeList = query.getResultList();
            return employeeList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
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
            throw new RuntimeException("No employee was found with name: " + name);
        }
    }

    @Override
    @Transactional
    public void saveEmployee(Employee employee) {
        try {
            session.save(employee);
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeException) {
                logger.error(runtimeException.getMessage());
            }
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
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
            throw new RuntimeException("No employee was found with id: " + id);
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
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updateEmployee(Employee employee) {
        try {
            session.beginTransaction();
            session.merge(employee);
            session.getTransaction().commit();
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeException) {
                logger.error(runtimeException.getMessage());
            }
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}


