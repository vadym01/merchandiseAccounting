package com.accounting.merchandiseAccounting.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String equipmentName;
    private boolean isWorkable;
    private Date dateOfReceipt;
    @OneToOne(mappedBy = "equipment")
    private Equipment equipment;

    public Equipment(long id, String equipmentName, boolean isWorkable, Date dateOfReceipt, Equipment equipment) {
        this.id = id;
        this.equipmentName = equipmentName;
        this.isWorkable = isWorkable;
        this.dateOfReceipt = dateOfReceipt;
        this.equipment = equipment;
    }

    public Equipment(String equipmentName, boolean isWorkable, Date dateOfReceipt, Equipment equipment) {
        this.equipmentName = equipmentName;
        this.isWorkable = isWorkable;
        this.dateOfReceipt = dateOfReceipt;
        this.equipment = equipment;
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

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
