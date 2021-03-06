package com.accounting.merchandiseAccounting.service;

import com.accounting.merchandiseAccounting.DTO.ProductForProceedDTO;
import com.accounting.merchandiseAccounting.DTO.ProductLoadedByEmployeeInfoDTO;
import com.accounting.merchandiseAccounting.model.Product;

import java.util.Date;
import java.util.List;

public interface ProductService {
    void saveProduct(Product product);
    Product findProductById(long id);
    List<Product> findProductByProductName(String productName);
    int deleteProductById(long id);
    List<Product> findAllProductsWhichIsNotProcessed();
    void updateProductProceedStatusById(long id);
    List<ProductForProceedDTO> getProductInfoForProceeding();
    void updateProductLoadedByEmployee(ProductLoadedByEmployeeInfoDTO productForProceedDto);
    ProductForProceedDTO getProductLoadedByEmployee(long INVNumber);
    List<ProductForProceedDTO> getProductHistoryByEmployeeId(long employeeId);
    List<ProductForProceedDTO> getProductInfoByDate(String shipment_date, boolean isPresent);

}
