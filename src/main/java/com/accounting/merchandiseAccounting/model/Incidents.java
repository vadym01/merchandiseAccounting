package com.accounting.merchandiseAccounting.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "incidents")
@NamedQueries({
        @NamedQuery(name = "findAllIncidents", query = "FROM Incidents"),
        @NamedQuery(name = "findIncidentById", query = "FROM Incidents WHERE id = :id"),
        @NamedQuery(name = "deleteIncidentById", query = "DELETE Incidents i WHERE id = :id"),
        @NamedQuery(name = "findIncidentsForVehicle", query = "FROM Incidents WHERE vehicle IS NOT NULL"),
        @NamedQuery(name = "findIncidentsForEmployee", query = "FROM Incidents WHERE employee IS NOT NULL")

//        SELECT e FROM Vehicle e WHERE e NOT IN (SELECT i.vehicle FROM Incidents i)")
})
public class Incidents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "incident_description", nullable = false)
    @Size(min = 2, message = "incidentDescription should be at list 2 characters")
    private String incidentDescription;
    @Column(name = "incident_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public Incidents(long id, String incidentDescription, Date date, Employee employee, Vehicle vehicle) {
        this.id = id;
        this.incidentDescription = incidentDescription;
        this.date = date;
        this.employee = employee;
        this.vehicle = vehicle;
    }

    public Incidents(String incidentDescription, Date date, Employee employee, Vehicle vehicle) {
        this.incidentDescription = incidentDescription;
        this.date = date;
        this.employee = employee;
        this.vehicle = vehicle;
    }

    public Incidents(String incidentDescription, Date date, Employee employee) {
        this.incidentDescription = incidentDescription;
        this.date = date;
        this.employee = employee;
    }

    public Incidents() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public void setIncidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "Incidents{" +
                "id=" + id +
                ", incidentDescription='" + incidentDescription + '\'' +
                ", date=" + date +
                ", employee=" + employee +
                ", vehicle=" + vehicle +
                '}';
    }
}
