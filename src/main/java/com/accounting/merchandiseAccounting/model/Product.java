package com.accounting.merchandiseAccounting.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product")
@NamedQueries({
        @NamedQuery(name = "getAllProducts", query = "FROM Product"),
        @NamedQuery(name = "deleteProductById", query = "DELETE Product WHERE id = :id"),
        @NamedQuery(name = "findProductById", query = "FROM Product WHERE id = :id"),
        @NamedQuery(name = "findProductByProductName", query = "FROM Product as p WHERE p.productName LIKE :productName"),
        @NamedQuery(name = "findAllProductsWitchIsNotProcessed", query = "FROM Product p WHERE p.isProcessed = false"),
        @NamedQuery(name = "updateProductProceedStatusById", query = "UPDATE Product p SET p.isProcessed = :isPrecessed WHERE p.id = :id")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INV_number", nullable = false)
    private long INVNumber;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "volume", nullable = false)
    private double volume;
    @Column(name = "weight", nullable = false)
    private double weight;
    @Column(name = "sender", nullable = false)
    private String Sender;
    @Column(name = "receiver", nullable = false)
    private String Receiver;
    @Column(name = "arrival_date", nullable = false)
    private Date arrivalDate;
    @Column(name = "is_present", columnDefinition = "boolean default true")
    private boolean isPresent;
    @Column(name = "is_processed", columnDefinition = "boolean default false")
    private boolean isProcessed;
    @ManyToOne()
    @JoinColumn(name = "loaded_by_employee_id")
    private Employee loadedByEmployee;
    @ManyToOne()
    @JoinColumn(name = "sent_by_employee_id")
    private Employee sentByEmployeeId;
    @Column(name = "shipment_date")
    private Date shipmentDate;

    public Product(long INVNumber, String productName, String description, double volume, double weight, String sender, String receiver, Date arrivalDate, boolean isPresent, boolean isProcessed, Employee loadedByEmployee, Employee sentByEmployeeId, Date shipmentDate) {
        this.INVNumber = INVNumber;
        this.productName = productName;
        this.description = description;
        this.volume = volume;
        this.weight = weight;
        Sender = sender;
        Receiver = receiver;
        this.arrivalDate = arrivalDate;
        this.isPresent = isPresent;
        this.isProcessed = isProcessed;
        this.loadedByEmployee = loadedByEmployee;
        this.sentByEmployeeId = sentByEmployeeId;
        this.shipmentDate = shipmentDate;
    }

    public Product(String productName, String description, double volume, double weight, String sender, String receiver, Date arrivalDate, boolean isPresent, boolean isProcessed, Employee loadedByEmployee, Employee sentByEmployeeId, Date shipmentDate) {
        this.productName = productName;
        this.description = description;
        this.volume = volume;
        this.weight = weight;
        Sender = sender;
        Receiver = receiver;
        this.arrivalDate = arrivalDate;
        this.isPresent = isPresent;
        this.isProcessed = isProcessed;
        this.loadedByEmployee = loadedByEmployee;
        this.sentByEmployeeId = sentByEmployeeId;
        this.shipmentDate = shipmentDate;
    }

    public Product() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
