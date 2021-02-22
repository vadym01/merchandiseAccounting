package com.accounting.merchandiseAccounting.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Incidents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "incident_description", nullable = false)
    private String incidentDescription;
    @Column(name = "incident_date", nullable = false)
    private Date date;

    public Incidents(long id, String incidentDescription, Date date) {
        this.id = id;
        this.incidentDescription = incidentDescription;
        this.date = date;
    }

    public Incidents(String incidentDescription, Date date) {
        this.incidentDescription = incidentDescription;
        this.date = date;
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
}
