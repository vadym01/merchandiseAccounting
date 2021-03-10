package com.accounting.merchandiseAccounting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.jfr.Name;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "equipment")
@NamedQueries({
        @NamedQuery(name = "findEquipmentById", query = "FROM Equipment WHERE id = :id"),
        @NamedQuery(name = "deleteEquipmentById", query = "DELETE Equipment WHERE id = :id"),
        @NamedQuery(name = "getAllEquipment", query = "FROM Equipment"),
        @NamedQuery(name = "updateAvailableStatusById", query = "UPDATE Equipment e SET e.isWorkable = :isWorkable WHERE e.id = :id"),
        @NamedQuery(name = "getAllAvailableEquipment", query = "SELECT e FROM Equipment e WHERE e NOT IN (SELECT i.equipment FROM Incidents i)"),
        @NamedQuery(name = "findEquipmentByEquipmentName", query = "FROM Equipment e WHERE e.equipmentName LIKE :equipmentName")
})
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "equipment_name", nullable = false)
    @Size(min = 2, message = "equipment name name should be at list 2 characters")
    private String equipmentName;
    @Column(name = "is_workable", nullable = false)
    private boolean isWorkable;
    @Column(name = "date_of_receipt", nullable = false)
    private Date dateOfReceipt;
    @Column(name = "lifting_capacity", nullable = false)
    private double liftingCapacity;
    @JsonIgnore
    @OneToOne(mappedBy = "equipment")
    private Incidents incidents;

    public Equipment(long id, String equipmentName, boolean isWorkable, Date dateOfReceipt, double liftingCapacity, Incidents incidents) {
        this.id = id;
        this.equipmentName = equipmentName;
        this.isWorkable = isWorkable;
        this.dateOfReceipt = dateOfReceipt;
        this.liftingCapacity = liftingCapacity;
        this.incidents = incidents;
    }

    public Equipment(String equipmentName, boolean isWorkable, Date dateOfReceipt, double liftingCapacity, Incidents incidents) {
        this.equipmentName = equipmentName;
        this.isWorkable = isWorkable;
        this.dateOfReceipt = dateOfReceipt;
        this.liftingCapacity = liftingCapacity;
        this.incidents = incidents;
    }

    public Equipment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public boolean isWorkable() {
        return isWorkable;
    }

    public void setWorkable(boolean workable) {
        isWorkable = workable;
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

    public Incidents getIncidents() {
        return incidents;
    }

    public void setIncidents(Incidents incidents) {
        this.incidents = incidents;
    }
}
