package com.accounting.merchandiseAccounting.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vehicle")
@NamedQueries({
        @NamedQuery(name = "findVehicleById", query = "FROM Vehicle WHERE id = :id"),
        @NamedQuery(name = "deleteVehicleById", query = "DELETE Vehicle WHERE id = :id"),
        @NamedQuery(name = "getAllVehicles", query = "FROM Vehicle"),
        @NamedQuery(name = "getAllAvailableVehicle", query = "SELECT v FROM Vehicle v WHERE v NOT IN (SELECT i.vehicle FROM Incident i)"),
        @NamedQuery(name = "findVehicleByVehicleName", query = "FROM Vehicle as v WHERE v.vehicleName LIKE :vehicleName"),
})
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "vehicle_name", nullable = false)
    @Size(min = 2, message = "vehicle name name should be at list 2 characters")
    private String vehicleName;
    @Column(name = "available_status")
    private boolean availableStatus;
    @Column(name = "date_of_receipt", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfReceipt;
    @Column(name = "lifting_capacity", nullable = false)
    private double liftingCapacity;
    @JsonIgnore
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<Incident> incident;

    public Vehicle(long id, String vehicleName,boolean availableStatus,  Date dateOfReceipt, double liftingCapacity, List<Incident> incident) {
        this.id = id;
        this.vehicleName = vehicleName;
        this.availableStatus = availableStatus;
        this.dateOfReceipt = dateOfReceipt;
        this.liftingCapacity = liftingCapacity;
        this.incident = incident;
    }

    public Vehicle(String vehicleName, Date dateOfReceipt,boolean availableStatus, double liftingCapacity, List<Incident> incident) {
        this.vehicleName = vehicleName;
        this.availableStatus = availableStatus;
        this.dateOfReceipt = dateOfReceipt;
        this.liftingCapacity = liftingCapacity;
        this.incident = incident;
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

    public boolean isAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(boolean availableStatus) {
        this.availableStatus = availableStatus;
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
