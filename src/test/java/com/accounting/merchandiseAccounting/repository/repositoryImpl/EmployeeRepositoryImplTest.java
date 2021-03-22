package com.accounting.merchandiseAccounting.repository.repositoryImpl;

import com.accounting.merchandiseAccounting.model.Employee;
import com.accounting.merchandiseAccounting.model.Incidents;
import com.accounting.merchandiseAccounting.repository.EmployeeRepository;
import com.accounting.merchandiseAccounting.repository.IncidentsRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmployeeRepositoryImplTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private IncidentsRepository incidentsRepository;


    @Autowired
    private EntityManager entityManager;
    Session session;


    private Employee employee;
    private Incidents incidents;
    private List<Employee> employeeList;

    private Date getDate(String date) {
        Date dateFromString;
        try {
            dateFromString = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            return dateFromString;
        } catch (ParseException e) {
            return null;
        }
    }

    @BeforeEach
    public void init() {
        employee = new Employee("Jhan", "Stewarts", "Anatolievich", getDate("1993-12-12"));
        incidents = new Incidents("testIncidentForEmployee", new Date(), employee);
        employeeList = Arrays.asList(
                new Employee("Jordan", "Albertson", "BAtikovitch", getDate("1993-05-18")),
                new Employee("Georg", "Allford", "BAtikovitch", getDate("1993-05-18")),
                new Employee("Jonny", "Cameroon", "BAtikovitch", getDate("2001-04-11")),
                new Employee("Oleg", "Adrian", "BAtikovitch", getDate("2020-11-16")),
                new Employee("Adolf", "Hit", "Germ", getDate("1993-12-12")));


        session = sessionFactory.openSession();
        session.getTransaction().begin();
        Query employeeQuery = session.createQuery("Delete from Employee");
        Query incidentsQuery = session.createQuery("Delete from Incidents");


        incidentsQuery.executeUpdate();
        employeeQuery.executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    @Transactional
    void findAllEmployees() {
        Query query = session.getNamedQuery("getAllEmployee");
        assertTrue(query.getResultList().isEmpty());
        employeeList.forEach(
                e -> session.save(e)
        );
        assertFalse(query.getResultList().isEmpty());
        assertTrue(employeeRepository.getAllEmployee().size() == employeeList.size());
    }

    @Test
    void saveEmployee() {
        List<Employee> employeeList = employeeRepository.getAllEmployee();
        session.beginTransaction();
        employeeRepository.saveEmployee(employee);
        session.getTransaction().commit();
        List<Employee> employeeAfterSaving = employeeRepository.getAllEmployee();
        assertTrue(employeeAfterSaving.contains(employee));
    }

    @Test
    void findEmployeeByName() {
        List<Employee> employeeList;
        employeeRepository.saveEmployee(employee);
        employeeList = employeeRepository.findEmployeeByName(employee.getFirstName());
        assertTrue(employeeList.contains(employee));
        employeeList = employeeRepository.findEmployeeByName(String.valueOf(employee.getFirstName().charAt(0)));
        assertTrue(employeeList.contains(employee));
    }

    @Test
    void getEmployeeById() {
        long employeeId = (long) session.save(employee);
        Employee employeeById = employeeRepository.getEmployeeById(employeeId);
        assertTrue(employeeById.getFirstName().equals(employee.getFirstName()));
        assertTrue(employeeById.getLastName().equals(employee.getLastName()));
    }

    @Test
    void getAllAvailableEmployees() {
        long employeeId = (long) session.save(employee);
        Query employeeQuery = session.getNamedQuery("getEmployeeById")
                .setParameter("id", employeeId);
        Employee employee = (Employee) employeeQuery.getSingleResult();
        long incidentId = (long) session.save(incidents);
        Query incidentsQuery = session.getNamedQuery("findIncidentById")
                .setParameter("id", incidentId);
        Incidents incidents = (Incidents) incidentsQuery.getSingleResult();
        incidents.setEmployee(employee);
        System.out.println(incidents.getEmployee());
        System.out.println(employee);
        assertThat(incidents.getEmployee(), not(employeeList.get(2)));
        assertThat(incidents.getEmployee(), is(employee));
        assertThat(employeeRepository.getAllAvailableEmployees().size(), is(0));
    }

    @Test
    void updateEmployee() {
        String originalEmployeeFirstName = employee.getFirstName();
        long employeeId = (long) session.save(employee);
        Query query = session.getNamedQuery("getEmployeeById")
                .setParameter("id", employeeId);
        Employee employeeForUpdate = (Employee) query.getSingleResult();
        employeeForUpdate.setFirstName("notJhan");
        employeeRepository.updateEmployee(employeeForUpdate);
        Employee employeeAfterUpdates = (Employee) query.getSingleResult();
        assertThat(originalEmployeeFirstName, not(employeeAfterUpdates.getFirstName()));
    }
}
