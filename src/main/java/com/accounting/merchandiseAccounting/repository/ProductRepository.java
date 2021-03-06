package com.accounting.merchandiseAccounting.repository;

import com.accounting.merchandiseAccounting.DTO.ProductForProceedDTO;
import com.accounting.merchandiseAccounting.DTO.ProductLoadedByEmployeeInfoDTO;
import com.accounting.merchandiseAccounting.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository{
    void saveProduct(Product product);
    Product findProductById(long id);
    List<Product> findProductByProductName(String productName);
    int deleteProductById(long id);
    List<Product> findAllProductsWhichIsNotProcessed();
    void updateProductProceedStatusById(long id);
    List<ProductForProceedDTO> getProductInfoForProceeding();
    void updateProductLoadedByEmployee(ProductLoadedByEmployeeInfoDTO productForProceedDto);
    ProductForProceedDTO getProductLoadedByEmployee(long id);
    List<ProductForProceedDTO> getProductHistoryByEmployeeId(long employeeId);
}
