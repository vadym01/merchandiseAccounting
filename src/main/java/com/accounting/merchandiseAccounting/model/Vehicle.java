package com.accounting.merchandiseAccounting.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vehicle")
@NamedQueries({
//        @NamedQuery(name = "findVehicleById", query = "FROM Vehicle WHERE id = :id"),
//        @NamedQuery(name = "deleteVehicleById", query = "DELETE Vehicle WHERE id = :id"),
//        @NamedQuery(name = "getAllVehicles", query = "FROM Vehicle"),
        @NamedQuery(name = "getAllAvailableVehicle", query = "SELECT v FROM Vehicle v WHERE v.available = true"),
        @NamedQuery(name = "findVehicleByVehicleName", query = "FROM Vehicle as v WHERE v.vehicleName LIKE :vehicleName"),
})
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "vehicle_name")
    @Size(min = 2, max = 40, message = "vehicle name should contain minimum 2 characters and maximum 40 characters")
    @NotNull(message = "vehicle name is required")
    private String vehicleName;
    @Column(name = "available")
    private boolean available = true;
    @Column(name = "date_of_receipt")
    @NotNull(message = "date of receipt is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfReceipt;
    @Column(name = "lifting_capacity")
    @DecimalMin(value = "0.1", message = "lifting capacity is required")
    private double liftingCapacity;
    @JsonIgnore
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.MERGE)
    private List<Incident> incident;

    public Vehicle(long id, String vehicleName, boolean available, Date dateOfReceipt, double liftingCapacity, List<Incident> incident) {
        this.id = id;
        this.vehicleName = vehicleName;
        this.available = available;
        this.dateOfReceipt = dateOfReceipt;
        this.liftingCapacity = liftingCapacity;
        this.incident = incident;
    }

    public Vehicle(String vehicleName, Date dateOfReceipt, boolean available, double liftingCapacity, List<Incident> incident) {
        this.vehicleName = vehicleName;
        this.available = available;
        this.dateOfReceipt = dateOfReceipt;
        this.liftingCapacity = liftingCapacity;
        this.incident = incident;
    }

    public Vehicle(String vehicleName, boolean available, Date dateOfReceipt, double liftingCapacity) {
        this.vehicleName = vehicleName;
        this.available = available;
        this.dateOfReceipt = dateOfReceipt;
        this.liftingCapacity = liftingCapacity;
    }

    public Vehicle() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Date getDateOfReceipt() {
        return dateOfReceipt;
    }

    public double getLiftingCapacity() {
        return liftingCapacity;
    }

    public void setLiftingCapacity(double liftingCapacity) {
        this.liftingCapacity = liftingCapacity;
    }

    public void setDateOfReceipt(Date dateOfReceipt) {
        this.dateOfReceipt = dateOfReceipt;
    }

    public List<Incident> getIncident() {
        return incident;
    }

    public void setIncident(List<Incident> incident) {
        this.incident = incident;
    }
}
