package com.accounting.merchandiseAccounting.controller;

import com.accounting.merchandiseAccounting.exceptions.ResourceNotFoundException;
import com.accounting.merchandiseAccounting.model.Employee;
import com.accounting.merchandiseAccounting.service.EmployeeService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> findEmployeeByFirstName(@RequestParam Optional<String> name){
        List<Employee> employeeList = employeeService.findEmployeeByName(name.orElse("_"));
        return new ResponseEntity<>(employeeList,HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> saveEmployee(@Validated @RequestBody Employee employee){
        employeeService.saveEmployee(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteEmployee( @PathVariable("id") long id) throws ResourceNotFoundException {
        int result = employeeService.deleteById(id);
        if(result == 0){
            throw new ResourceNotFoundException("entity with id: " + id + " not found");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findOneById(@PathVariable("id")long id) throws ResourceNotFoundException {
        Employee employee = employeeService.findOneById(id);
        if(employee == null){
            throw new ResourceNotFoundException("Employee with id: " + id + " is not present");
        }
        return new ResponseEntity<>(employee,HttpStatus.OK);
    }



}
