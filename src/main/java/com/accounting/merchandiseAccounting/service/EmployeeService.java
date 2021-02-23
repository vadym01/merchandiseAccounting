package com.accounting.merchandiseAccounting.service;


import com.accounting.merchandiseAccounting.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeService {
    List<Employee> getAllEmployees();
    void saveEmployee(Employee employee);
    int deleteById(long id);
    Employee findOneById(long id);
}
