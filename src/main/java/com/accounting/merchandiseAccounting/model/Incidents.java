package com.accounting.merchandiseAccounting.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "incidents")
@NamedQueries({
        @NamedQuery(name = "findAllIncidents", query = "FROM Incidents"),
        @NamedQuery(name = "findIncidentById", query = "FROM Incidents WHERE id = :id"),
        @NamedQuery(name = "deleteIncidentById", query = "DELETE Incidents WHERE id = :id")
})
public class Incidents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "incident_description", nullable = false)
    private String incidentDescription;
    @Column(name = "incident_date", nullable = false)
    private Date date;
    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @OneToOne
    @JoinColumn(name="equipment_id")
    private Equipment equipment;

    public Incidents(long id, String incidentDescription, Date date, Employee employee, Equipment equipment) {
        this.id = id;
        this.incidentDescription = incidentDescription;
        this.date = date;
        this.employee = employee;
        this.equipment = equipment;
    }

    public Incidents(String incidentDescription, Date date, Employee employee, Equipment equipment) {
        this.incidentDescription = incidentDescription;
        this.date = date;
        this.employee = employee;
        this.equipment = equipment;
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

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
