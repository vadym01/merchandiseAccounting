package com.accounting.merchandiseAccounting.repository;

import com.accounting.merchandiseAccounting.dto.ProductForProceedDTO;
import com.accounting.merchandiseAccounting.dto.ProductLoadedByEmployeeInfoDTO;
import com.accounting.merchandiseAccounting.model.Product;
import com.accounting.merchandiseAccounting.repository.crudRepository.CrudProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository{
    Product saveOrUpdateProduct(Product product);

//    Product findProductById(long id);

    List<Product> findProductsByProductName(String productName);

//    int deleteProductById(long id);

    List<Product> findAllProductsWhichIsNotProcessed();

//    void updateProductProceedStatusById(long id);

    List<ProductForProceedDTO> getProductInfoForProceeding();

    void updateProductLoadedByEmployee(ProductLoadedByEmployeeInfoDTO productForProceedDto);

    ProductForProceedDTO getProductLoadedByEmployee(long id);

    List<ProductForProceedDTO> getProductHistoryByEmployeeId(long employeeId);

    List<ProductForProceedDTO> getProductInfoByDate(Date shipment_date, boolean isPresent);

    void updateShipmentValueForSentBy(long employeeId, long INVNumber);

    void updateShipmentValueForIsPresent(long INVNumber, boolean isPresent);

    long getTotalAmountOfProducts();

}
