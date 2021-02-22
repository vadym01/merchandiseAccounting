package com.accounting.merchandiseAccounting.repository;

import com.accounting.merchandiseAccounting.DTO.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeesRepository extends CrudRepository<Employee, Long> {

    List<Employee> getAllEmployee();
//    void saveEmployee(Employee employee);
//    void deleteEmployeeById(long id);
//    Employee finEmployeeById(long id);
//    void updateEmployee(Employee employee);

}
