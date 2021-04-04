package com.accounting.merchandiseAccounting.dto;

public class ProductLoadedByEmployeeInfoDTO {
    private long currentEmployeeId;
    private long productId;

    public ProductLoadedByEmployeeInfoDTO(long currentEmployeeId, long productId) {
        this.currentEmployeeId = currentEmployeeId;
        this.productId = productId;
    }

    public ProductLoadedByEmployeeInfoDTO() {
    }

    public long getCurrentEmployeeId() {
        return currentEmployeeId;
    }

    public void setCurrentEmployeeId(long currentEmployeeId) {
        this.currentEmployeeId = currentEmployeeId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
