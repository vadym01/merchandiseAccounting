package com.accounting.merchandiseAccounting.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "product")
@NamedQueries({
        @NamedQuery(name = "getAllProducts", query = "FROM Product"),
        @NamedQuery(name = "deleteProductById", query = "DELETE Product WHERE INVNumber = :id"),
        @NamedQuery(name = "findProductById", query = "FROM Product WHERE INVNumber = :id"),
        @NamedQuery(name = "findProductByProductName", query = "FROM Product as p WHERE p.productName LIKE :productName"),
        @NamedQuery(name = "findAllProductsWhichIsNotProcessed", query = "FROM Product p WHERE p.isProcessed = false"),
        @NamedQuery(name = "updateProductProceedStatusById", query = "UPDATE Product p SET p.isProcessed = :isPrecessed WHERE p.INVNumber = :id"),
        @NamedQuery(name = "getProductInfoForProceeding", query = "SELECT p.INVNumber as INVNumber, p.productName as productName," +
                " p.description as description, p.volume as volume, p.weight as weight," +
                " p.receiptDate as receiptDate," +
                " p.scheduledShipmentDate as scheduledShipmentDate," +
                " p.arrivalDate as arrivalDate," +
                " p.shipmentDate as shipmentDate" +
                " FROM Product p WHERE p.isProcessed = false AND p.loadedByEmployee = NULL ORDER BY p.arrivalDate ASC"),
        @NamedQuery(name = "updateProductLoadedByEmployee", query = "UPDATE Product p SET p.loadedByEmployee = :loadedByEmployee" +
                " WHERE p.INVNumber = :INVNumber"),
        @NamedQuery(name = "getProductLoadedByEmployee", query = "SELECT p.INVNumber as INVNumber, p.productName as productName," +
                " p.description as description, p.volume as volume, p.weight as weight," +
                " p.arrivalDate as arrivalDate," +
                " p.shipmentDate as shipmentDate" +
                " FROM Product p WHERE p.INVNumber = :INVNumber"),
        @NamedQuery(name = "getProductHistoryByEmployeeId", query = "SELECT p.INVNumber as INVNumber, p.productName as productName," +
                " p.description as description, p.volume as volume, p.weight as weight," +
                " p.arrivalDate as arrivalDate," +
                " p.shipmentDate as shipmentDate," +
                "p.scheduledShipmentDate as scheduledShipmentDate," +
                "p.receiptDate as receiptDate" +
                " FROM Product p WHERE p.loadedByEmployee = :loadedByEmployee"),
        @NamedQuery(name = "getProductInfoByDate", query = "SELECT p.INVNumber as INVNumber, p.productName as productName," +
                " p.description as description, p.volume as volume, p.weight as weight," +
                " p.receiptDate as receiptDate," +
                " p.scheduledShipmentDate as scheduledShipmentDate," +
                " p.arrivalDate as arrivalDate," +
                " p.shipmentDate as shipmentDate" +
                " FROM Product p WHERE scheduledShipmentDate <= :scheduledShipmentDate " +
                "AND isPresent = :isPresent " +
                "AND isProcessed = true"),
        @NamedQuery(name = "updateShipmentValueForSentBy", query = "UPDATE Product  SET sentByEmployee = :sentByEmployee," +
                "shipmentDate = :shipmentDate " +
                "WHERE INVNumber = :INVNumber"),
        @NamedQuery(name = "updateShipmentValueForIsPresent", query = "UPDATE Product p SET p.isPresent = :isPresent " +
                "WHERE p.INVNumber = :INVNumber"),
        @NamedQuery(name = "getTotalAmountOfProducts", query = "SELECT COUNT(p.INVNumber) as total from Product p")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INV_number")
    private long INVNumber;
    @Column(name = "product_name")
    @Size(min = 2, max = 40,
            message = "product name/identifier should contain minimum 2 characters and maximum 40 characters")
    @NotNull(message = "product name/identifier is required")
    private String productName;
    @Column(name = "description")
    @Size(min = 2, max = 60,
            message = "description should contain minimum 2 characters and maximum 60 characters")
    @NotNull(message = "description is required")
    private String description;
    @Column(name = "volume")
    @DecimalMin(value = "0.01", message = "minimum value for volume is 0.1")
    @DecimalMax(value = "50.0", message = "maximum value for volume is 50.0")
    private double volume;
    @Column(name = "weight")
    @DecimalMin(value = "0.01", message = "minimum value for weight is 0.1")
    @DecimalMax(value = "1000.0", message = "maximum value for weight is 1000.0")
    private double weight;
    @Column(name = "sender")
    @Size(min = 2, max = 40,
            message = "sender identifier should contain minimum 2 characters and maximum 40 characters")
    @NotNull(message = "sender is required")
    private String sender;
    @Column(name = "receiver")
    @Size(min = 2, max = 40,
            message = "receiver identifier should contain minimum 2 characters and maximum 40 characters")
    @NotNull(message = "receiver is required")
    private String receiver;
    @Column(name = "receipt_date")
    @NotNull(message = "receipt date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date receiptDate;
    @Column(name = "scheduled_shipment_date")
    @NotNull(message = "scheduled shipment date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date scheduledShipmentDate;
    @Column(name = "arrival_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date arrivalDate;
    @Column(name = "is_present", columnDefinition = "boolean default true")
    private boolean isPresent = true;
    @Column(name = "is_processed", columnDefinition = "boolean default false")
    private boolean isProcessed = false;
    @ManyToOne()
    @JoinColumn(name = "loaded_by_employee_id", insertable = false, updatable = false)
    private Employee loadedByEmployee;
    @ManyToOne()
    @JoinColumn(name = "sent_by_employee_id", insertable = false, updatable = false)
    private Employee sentByEmployee;
    @Column(name = "shipment_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date shipmentDate;


    public Product(long INVNumber, String productName, String description, double volume, double weight, String sender, String receiver, Date receiptDate, Date scheduledShipmentDate, Date arrivalDate, boolean isPresent, boolean isProcessed, Employee loadedByEmployee, Employee sentByEmployee, Date shipmentDate) {
        this.INVNumber = INVNumber;
        this.productName = productName;
        this.description = description;
        this.volume = volume;
        this.weight = weight;
        this.sender = sender;
        this.receiver = receiver;
        this.receiptDate = receiptDate;
        this.scheduledShipmentDate = scheduledShipmentDate;
        this.arrivalDate = arrivalDate;
        this.isPresent = isPresent;
        this.isProcessed = isProcessed;
        this.loadedByEmployee = loadedByEmployee;
        this.sentByEmployee = sentByEmployee;
        this.shipmentDate = shipmentDate;
    }

    public Product(String productName, String description, double volume, double weight, String sender, String receiver, Date receiptDate, Date scheduledShipmentDate, Date arrivalDate, boolean isPresent, boolean isProcessed, Employee loadedByEmployee, Employee sentByEmployee, Date shipmentDate) {
        this.productName = productName;
        this.description = description;
        this.volume = volume;
        this.weight = weight;
        this.sender = sender;
        this.receiver = receiver;
        this.receiptDate = receiptDate;
        this.scheduledShipmentDate = scheduledShipmentDate;
        this.arrivalDate = arrivalDate;
        this.isPresent = isPresent;
        this.isProcessed = isProcessed;
        this.loadedByEmployee = loadedByEmployee;
        this.sentByEmployee = sentByEmployee;
        this.shipmentDate = shipmentDate;
    }

    public Product(String productName, String description, double volume, double weight, String sender, String receiver, Date receiptDate, Date scheduledShipmentDate, Date arrivalDate, boolean isPresent, boolean isProcessed, Date shipmentDate) {
        this.productName = productName;
        this.description = description;
        this.volume = volume;
        this.weight = weight;
        this.sender = sender;
        this.receiver = receiver;
        this.receiptDate = receiptDate;
        this.scheduledShipmentDate = scheduledShipmentDate;
        this.arrivalDate = arrivalDate;
        this.isPresent = isPresent;
        this.isProcessed = isProcessed;
        this.shipmentDate = shipmentDate;
    }

    public Product() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return INVNumber == product.INVNumber && Double.compare(product.volume, volume) == 0 && Double.compare(product.weight, weight) == 0 && isPresent == product.isPresent && isProcessed == product.isProcessed && Objects.equals(productName, product.productName) && Objects.equals(description, product.description) && Objects.equals(sender, product.sender) && Objects.equals(receiver, product.receiver) && Objects.equals(receiptDate, product.receiptDate) && Objects.equals(scheduledShipmentDate, product.scheduledShipmentDate) && Objects.equals(arrivalDate, product.arrivalDate) && Objects.equals(loadedByEmployee, product.loadedByEmployee) && Objects.equals(sentByEmployee, product.sentByEmployee) && Objects.equals(shipmentDate, product.shipmentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(INVNumber, productName, description, volume, weight, sender, receiver, receiptDate, scheduledShipmentDate, arrivalDate, isPresent, isProcessed, loadedByEmployee, sentByEmployee, shipmentDate);
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public Date getScheduledShipmentDate() {
        return scheduledShipmentDate;
    }

    public void setScheduledShipmentDate(Date scheduledShipmentDate) {
        this.scheduledShipmentDate = scheduledShipmentDate;
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

    public Employee getSentByEmployee() {
        return sentByEmployee;
    }

    public void setSentByEmployee(Employee sentByEmployee) {
        this.sentByEmployee = sentByEmployee;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "INVNumber=" + INVNumber +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", volume=" + volume +
                ", weight=" + weight +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", receiptDate=" + receiptDate +
                ", scheduledShipmentDate=" + scheduledShipmentDate +
                ", arrivalDate=" + arrivalDate +
                ", isPresent=" + isPresent +
                ", isProcessed=" + isProcessed +
                ", loadedByEmployee=" + loadedByEmployee +
                ", sentByEmployee=" + sentByEmployee +
                ", shipmentDate=" + shipmentDate +
                '}';
    }
}
