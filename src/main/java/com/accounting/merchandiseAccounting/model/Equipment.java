package com.accounting.merchandiseAccounting.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "equipment_name", nullable = false)
    private String equipmentName;
    @Column(name = "is_workable", nullable = false)
    private boolean isWorkable;
    @Column(name = "date_of_receipt", nullable = false)
    private Date dateOfReceipt;
    @OneToOne(mappedBy = "equipment")
    private Incidents incidents;

    public Equipment(long id, String equipmentName, boolean isWorkable, Date dateOfReceipt, Incidents incidents) {
        this.id = id;
        this.equipmentName = equipmentName;
        this.isWorkable = isWorkable;
        this.dateOfReceipt = dateOfReceipt;
        this.incidents = incidents;
    }

    public Equipment(String equipmentName, boolean isWorkable, Date dateOfReceipt, Incidents incidents) {
        this.equipmentName = equipmentName;
        this.isWorkable = isWorkable;
        this.dateOfReceipt = dateOfReceipt;
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
