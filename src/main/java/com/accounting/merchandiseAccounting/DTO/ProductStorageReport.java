package com.accounting.merchandiseAccounting.DTO;

public class ProductStorageReport {

    private int total;
    private int spaceAvailable;

    public ProductStorageReport(int total, int spaceAvailable) {
        this.total = total;
        this.spaceAvailable = spaceAvailable;
    }

    public ProductStorageReport() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSpaceAvailable() {
        return spaceAvailable;
    }

    public void setSpaceAvailable(int spaceAvailable) {
        this.spaceAvailable = spaceAvailable;
    }
}
