package com.accounting.merchandiseAccounting.repository.repositoryImpl;

import com.accounting.merchandiseAccounting.exceptions.GlobalExceptionHandler;
import com.accounting.merchandiseAccounting.exceptions.ResourceNotFoundException;
import com.accounting.merchandiseAccounting.model.Employee;
import com.accounting.merchandiseAccounting.repository.EmployeeRepository;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.orm.hibernate5.HibernateJdbcException;
import org.springframework.orm.hibernate5.HibernateQueryException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Queue;

@Service
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final static Logger logger = LogManager.getLogger(EmployeeRepositoryImpl.class);

    @Autowired
    private EntityManager entityManager;

    @Autowired
    SessionFactory sessionFactory;
    Session session;

    @PostConstruct
    public void init() {
        session = sessionFactory.openSession();
    }

    @Transactional
    @Override
    public List<Employee> getAllEmployee() {
        List employeeList;
        try {
            Query query = session.getNamedQuery("getAllEmployee");
            employeeList = query.list();
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
        return employeeList;
    }

    @Transactional
    @Override
    public void saveEmployee(Employee employee) {
            session.save(employee);
    }

    @Transactional
    @Override
    public int deleteEmployeeById(long id) {
        try {
            Query query = session.getNamedQuery("deleteEmployeeById");
            query.setParameter("id", id);
            Transaction transaction = session.beginTransaction();
            int num = query.executeUpdate();
            transaction.commit();
            return num;
        }catch (Exception e){
            logger.error(e.getMessage());
            return 0;
        }
    }

    @Override
    public Employee getEmployeeById(long id) {
        try {
            Query query = session.getNamedQuery("getEmployeeById").setParameter("id", id);
            Employee employee = (Employee) query.list().get(0);
            return employee;
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }
}
