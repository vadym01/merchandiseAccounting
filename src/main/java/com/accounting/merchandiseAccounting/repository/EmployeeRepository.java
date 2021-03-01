package com.accounting.merchandiseAccounting.repository;

import com.accounting.merchandiseAccounting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface EmployeeRepository {

    List<Employee> getAllEmployee();
    void saveEmployee(Employee employee);
    int deleteEmployeeById(long id);
    Employee getEmployeeById(long id);
    List<Employee> findEmployeeByName(String name);
//    void updateEmployee(Employee employee);
}
