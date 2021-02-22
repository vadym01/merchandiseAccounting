package com.accounting.merchandiseAccounting.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INV_number", nullable = false)
    private long INVNumber;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "volume", nullable = false)
    private double volume;
    @Column(name="sender", nullable = false)
    private String Sender;
    @Column(name = "receiver", nullable = false)
    private String Receiver;
    @Column(name="arrival_date", nullable = false)
    private Date arrivalDate;
    @ManyToOne()
    @JoinColumn(name = "loaded_by_employee_id" )
    private Employee loadedByEmployee;
    @ManyToOne()
    @JoinColumn(name = "sent_by_employee_id")
    private Employee sentByEmployeeId;
    @Column(name = "shipment_date",nullable = false)
    private Date shipmentDate;

    public Product(long INVNumber, String description, double volume, String sender, String receiver, Date arrivalDate, Employee loadedByEmployee, Employee sentByEmployeeId, Date shipmentDate) {
        this.INVNumber = INVNumber;
        this.description = description;
        this.volume = volume;
        Sender = sender;
        Receiver = receiver;
        this.arrivalDate = arrivalDate;
        this.loadedByEmployee = loadedByEmployee;
        this.sentByEmployeeId = sentByEmployeeId;
        this.shipmentDate = shipmentDate;
    }

    public Product(String description, double volume, String sender, String receiver, Date arrivalDate, Employee loadedByEmployee, Employee sentByEmployeeId, Date shipmentDate) {
        this.description = description;
        this.volume = volume;
        Sender = sender;
        Receiver = receiver;
        this.arrivalDate = arrivalDate;
        this.loadedByEmployee = loadedByEmployee;
        this.sentByEmployeeId = sentByEmployeeId;
        this.shipmentDate = shipmentDate;
    }

    public Product() {
    }

    public long getINVNumber() {
        return INVNumber;
    }

    public void setINVNumber(long INVNumber) {
        this.INVNumber = INVNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Employee getLoadedByEmployee() {
        return loadedByEmployee;
    }

    public void setLoadedByEmployee(Employee loadedByEmployee) {
        this.loadedByEmployee = loadedByEmployee;
    }

    public Employee getSentByEmployeeId() {
        return sentByEmployeeId;
    }

    public void setSentByEmployeeId(Employee sentByEmployeeId) {
        this.sentByEmployeeId = sentByEmployeeId;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
