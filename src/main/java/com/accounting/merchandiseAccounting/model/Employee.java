package com.accounting.merchandiseAccounting.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "employee")
@NamedQueries({
//        @NamedQuery(name = "getAllEmployee", query = "FROM Employee "),
//        @NamedQuery(name = "getEmployeeById", query = "FROM Employee WHERE id = :id"),
        @NamedQuery(name = "findEmployeeByName", query = "FROM Employee as e WHERE e.firstName LIKE :firstName"),
        @NamedQuery(name = "getAllAvailableEmployees",
                query = "SELECT e FROM Employee e WHERE e.available = true")
})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "first_name")
    @Size(min = 2, max = 40,
            message = "first name should contain minimum 2 characters and maximum 40 characters")
    @NotBlank(message = "first name is required")
    private String firstName;
    @Column(name = "last_name")
    @Size(min = 2, max = 40, message = "last name should contain minimum 2 characters and maximum 40 characters")
    @NotBlank(message = "last name is required")
    private String lastName;
    @Column(name = "patronymic")
    @Size(min = 2, max = 40, message = "patronymic should contain minimum 2 characters and maximum 40 characters")
    @NotBlank(message = "patronymic is required")
    private String patronymic;
    @Column(name = "available")
    private boolean available = true;
    @Column(name = "dob")
    @NotNull(message = "birth date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date DOB;
    @JsonIgnore
    @OneToMany(mappedBy = "loadedByEmployee", cascade = CascadeType.PERSIST)
    private List<Product> productListLoadedByEmployee = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "sentByEmployee", cascade = CascadeType.PERSIST)
    private List<Product> productListSentByEmployee = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "employee",cascade = CascadeType.MERGE)
    private List<Incident> incident;


    public Employee(long id, String firstName, String lastName, String patronymic, boolean available, Date DOB, List<Product> productListLoadedByEmployee, List<Product> productListSentByEmployee, List<Incident> incident) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.available = available;
        this.DOB = DOB;
        this.productListLoadedByEmployee = productListLoadedByEmployee;
        this.productListSentByEmployee = productListSentByEmployee;
        this.incident = incident;
    }

    public Employee(String firstName, String lastName, String patronymic, boolean available, Date DOB, List<Product> productListLoadedByEmployee, List<Product> productListSentByEmployee, List<Incident> incident) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.available = available;
        this.DOB = DOB;
        this.productListLoadedByEmployee = productListLoadedByEmployee;
        this.productListSentByEmployee = productListSentByEmployee;
        this.incident = incident;
    }

    public Employee(String firstName, String lastName, String patronymic, boolean available, Date DOB) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.available = available;
        this.DOB = DOB;
    }

    public Employee() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public List<Product> getProductListLoadedByEmployee() {
        return productListLoadedByEmployee;
    }

    public void setProductListLoadedByEmployee(List<Product> productListLoadedByEmployee) {
        this.productListLoadedByEmployee = productListLoadedByEmployee;
    }

    public List<Product> getProductListSentByEmployee() {
        return productListSentByEmployee;
    }

    public void setProductListSentByEmployee(List<Product> productListSentByEmployee) {
        this.productListSentByEmployee = productListSentByEmployee;
    }

    public List<Incident> getIncident() {
        return incident;
    }

    public void setIncident(List<Incident> incident) {
        this.incident = incident;
    }

}
