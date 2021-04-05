package com.accounting.merchandiseAccounting.service.serviceImpl;

import com.accounting.merchandiseAccounting.model.Employee;
//import com.accounting.merchandiseAccounting.repository.EmployeeRepository;
import com.accounting.merchandiseAccounting.repository.EmployeeRepository;
import com.accounting.merchandiseAccounting.repository.crudRepository.CrudProvider;
import com.accounting.merchandiseAccounting.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private CrudProvider<Employee> crudProvider;

    @Autowired
    public void setCrudProvider(CrudProvider<Employee> crudProvider) {
        this.crudProvider = crudProvider;
        this.crudProvider.setClassInstance(Employee.class);
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.addAll(crudProvider.findAll());
        return employeeList;
    }

    @Override
    public List<Employee> findEmployeesByName(String name) {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.addAll(employeeRepository.findEmployeesByName(name));
        return employeeList;
    }

    @Override
    public void saveEmployee(Employee employee) {
        crudProvider.save(employee);
    }

    @Override
    public Employee findOneById(long id) {
        Employee employeeById = crudProvider.findOneById(id);
        return employeeById;
    }

    @Override
    public List<Employee> getAllAvailableEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.addAll(employeeRepository.getAllAvailableEmployees());
        return employeeList;
    }

    @Override
    public void updateEmployee(Employee employee) {
        crudProvider.update(employee);
    }

    @Override
    @Transactional
    public void changeAvailableStatusForEmployee(long id) {
        Employee employee = findOneById(id);
        employee.setAvailable(!employee.isAvailable());
        updateEmployee(employee);
    }
}
