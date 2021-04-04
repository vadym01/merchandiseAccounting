package com.accounting.merchandiseAccounting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ProductForProceedDTO {
    private long INVNumber;
    private String productName;
    private String description;
    private double volume;
    private double weight;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date arrivalDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date shipmentDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date scheduledShipmentDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date receiptDate;

    public ProductForProceedDTO(long INVNumber, String productName, String description, double volume, double weight, Date arrivalDate, Date shipmentDate, Date scheduledShipmentDate, Date receiptDate) {
        this.INVNumber = INVNumber;
        this.productName = productName;
        this.description = description;
        this.volume = volume;
        this.weight = weight;
        this.arrivalDate = arrivalDate;
        this.shipmentDate = shipmentDate;
        this.scheduledShipmentDate = scheduledShipmentDate;
        this.receiptDate = receiptDate;
    }

    public ProductForProceedDTO(String productName, String description, double volume, double weight, Date arrivalDate, Date shipmentDate, Date scheduledShipmentDate, Date receiptDate) {
        this.productName = productName;
        this.description = description;
        this.volume = volume;
        this.weight = weight;
        this.arrivalDate = arrivalDate;
        this.shipmentDate = shipmentDate;
        this.scheduledShipmentDate = scheduledShipmentDate;
        this.receiptDate = receiptDate;
    }

    public ProductForProceedDTO() {
    }

    public long getINVNumber() {
        return INVNumber;
    }

    public void setINVNumber(long INVNumber) {
        this.INVNumber = INVNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public Date getScheduledShipmentDate() {
        return scheduledShipmentDate;
    }

    public void setScheduledShipmentDate(Date scheduledShipmentDate) {
        this.scheduledShipmentDate = scheduledShipmentDate;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }
}
