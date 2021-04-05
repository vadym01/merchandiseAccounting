package com.accounting.merchandiseAccounting.controller;

import com.accounting.merchandiseAccounting.model.Employee;
import com.accounting.merchandiseAccounting.service.EmployeeService;
import com.accounting.merchandiseAccounting.validationService.MapValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MapValidationService mapValidationService;

    @GetMapping("find/")
    public ResponseEntity<List<Employee>> findEmployeeByFirstName(@RequestParam Optional<String> name) {
        List<Employee> employeeList = employeeService.findEmployeesByName(name.orElse("_"));
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllEmployee() {
        List<Employee> employeeList = employeeService.getAllEmployees();
        return new ResponseEntity(employeeList, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> saveEmployee(@Validated @RequestBody Employee employee, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = mapValidationService.mapValidationService(bindingResult);
        if (errorMap != null) return errorMap;
        employeeService.saveEmployee(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findOneById(@PathVariable("id") long id) {
        Employee employee = employeeService.findOneById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }


    @GetMapping("available")
    public ResponseEntity<?> getAllAvailableEmployee() {
        List<Employee> employeeList = employeeService.getAllAvailableEmployees();
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> changeEmployee(@Validated @RequestBody Employee employee, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = mapValidationService.mapValidationService(bindingResult);
        if (errorMap != null) return errorMap;
        employeeService.updateEmployee(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
