package com.accounting.merchandiseAccounting.dto;

public class ProductReportDto {
    private double totalVolume;
    private long totalUnits;

    public ProductReportDto(double totalVolume, long totalUnits) {
        this.totalVolume = totalVolume;
        this.totalUnits = totalUnits;
    }

    public ProductReportDto() {
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public long getTotalUnits() {
        return totalUnits;
    }

    public void setTotalUnits(long totalUnits) {
        this.totalUnits = totalUnits;
    }
}
