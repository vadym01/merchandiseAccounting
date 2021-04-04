package com.accounting.merchandiseAccounting.dto;

public class EquipmentStatus {
    private boolean isWorkable;
    private String someStaring;


    public EquipmentStatus(boolean isWorkable, String someStaring) {
        this.isWorkable = isWorkable;
        this.someStaring = someStaring;
    }

    public EquipmentStatus() {
    }

    public boolean isWorkable() {
        return isWorkable;
    }

    public void setWorkable(boolean workable) {
        isWorkable = workable;
    }

    @Override
    public String toString() {
        return "EquipmentStatus{" +
                "isWorkable=" + isWorkable +
                ", someStaring='" + someStaring + '\'' +
                '}';
    }
}
