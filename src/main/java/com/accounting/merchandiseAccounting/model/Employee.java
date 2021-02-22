package com.accounting.merchandiseAccounting.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name="patronymic")
    private String patronymic;
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;
    @OneToMany(mappedBy = "loaded_by_employee_id", fetch = FetchType.LAZY)
    @Column(nullable = false)
    private List<Product> productListLoadedByEmployee = new ArrayList<>();
    @OneToMany(mappedBy = "sent_by_employee_id", fetch = FetchType.LAZY)
    private List<Product> productListSentByEmployee = new ArrayList<>();
    @OneToOne(mappedBy = "employee")
    private Incidents incidents;


    public Employee(long id, String firstName, String lastName, String patronymic, Date birthDate, List<Product> productListLoadedByEmployee, List<Product> productListSentByEmployee,Incidents incidents) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.productListLoadedByEmployee = productListLoadedByEmployee;
        this.productListSentByEmployee = productListSentByEmployee;
        this.incidents = incidents;
    }

    public Employee(String firstName, String lastName, String patronymic, Date birthDate, List<Product> productListLoadedByEmployee, List<Product> productListSentByEmployee, Incidents incidents) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.productListLoadedByEmployee = productListLoadedByEmployee;
        this.productListSentByEmployee = productListSentByEmployee;
        this.incidents = incidents;
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

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    public Incidents getIncidents() {
        return incidents;
    }

    public void setIncidents(Incidents incidents) {
        this.incidents = incidents;
    }
}
