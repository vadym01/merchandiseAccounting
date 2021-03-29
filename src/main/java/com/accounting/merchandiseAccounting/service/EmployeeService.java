package com.accounting.merchandiseAccounting.service;


import com.accounting.merchandiseAccounting.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeService {
    List<Employee> getAllEmployees();
    void saveEmployee(Employee employee);
    Employee findOneById(long id);
    List<Employee> findEmployeeByName(String name);
    List<Employee> getAllAvailableEmployees();
    void updateEmployee(Employee employee);
    void changeAvailableStatusForEmployee(long id);

}
