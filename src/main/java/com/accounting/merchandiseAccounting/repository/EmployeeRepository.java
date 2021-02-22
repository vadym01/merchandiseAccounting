package com.accounting.merchandiseAccounting.repository;

import com.accounting.merchandiseAccounting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    List<Employee> getAllEmployee();
//    void saveEmployee(Employee employee);
//    void deleteEmployeeById(long id);
//    Employee finEmployeeById(long id);
//    void updateEmployee(Employee employee);
}
