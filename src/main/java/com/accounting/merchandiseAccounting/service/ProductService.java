package com.accounting.merchandiseAccounting.service;

import com.accounting.merchandiseAccounting.DTO.ProductForProceedDTO;
import com.accounting.merchandiseAccounting.DTO.ProductLoadedByEmployeeInfoDTO;
import com.accounting.merchandiseAccounting.model.Product;
import com.accounting.merchandiseAccounting.DTO.ProductStorageReport;

import java.util.Date;
import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);

    Product findProductById(long id);

    List<Product> findProductByProductName(String productName);

    int deleteProductById(long id);

    List<Product> findAllProductsWhichIsNotProcessed();

    void updateProductProceedStatusById(long id);

    List<ProductForProceedDTO> getProductInfoForProceeding();

    void updateProductLoadedByEmployee(ProductLoadedByEmployeeInfoDTO productForProceedDto);

    ProductForProceedDTO getProductLoadedByEmployee(long INVNumber);

    List<ProductForProceedDTO> getProductHistoryByEmployeeId(long employeeId);

    List<ProductForProceedDTO> getProductInfoByDate(Date shipment_date, boolean isPresent);

    void updateShipmentValueForSentBy(long employeeId, long INVNumber);

    void updateShipmentValueForIsPresent(long INVNumber, boolean isPresent);

    ProductStorageReport getTotalAmountOfProducts();
}
