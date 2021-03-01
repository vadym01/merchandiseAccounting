package com.accounting.merchandiseAccounting.service.serviceImpl;

import com.accounting.merchandiseAccounting.model.Employee;
//import com.accounting.merchandiseAccounting.repository.EmployeeRepository;
import com.accounting.merchandiseAccounting.repository.EmployeeRepository;
import com.accounting.merchandiseAccounting.service.EmployeeService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.getAllEmployee();
        return employeeList;
    }

    @Override
    public List<Employee> findEmployeeByName(String name) {
        List<Employee> employeeList = employeeRepository.findEmployeeByName(name);
        return employeeList;
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.saveEmployee(employee);
    }

    @Override
    public int deleteById(long id) {
        int result = employeeRepository.deleteEmployeeById(id);
        return result;
    }

    @Override
    public Employee findOneById(long id) {
        Employee employeeById = employeeRepository.getEmployeeById(id);
        return employeeById;
    }
}
