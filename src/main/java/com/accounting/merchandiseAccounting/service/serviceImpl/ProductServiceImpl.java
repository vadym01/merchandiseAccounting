package com.accounting.merchandiseAccounting.service.serviceImpl;

import com.accounting.merchandiseAccounting.DTO.ProductForProceedDTO;
import com.accounting.merchandiseAccounting.DTO.ProductLoadedByEmployeeInfoDTO;
import com.accounting.merchandiseAccounting.exceptions.CustomExceptionHandler;
import com.accounting.merchandiseAccounting.model.Employee;
import com.accounting.merchandiseAccounting.model.Product;
import com.accounting.merchandiseAccounting.DTO.ProductStorageReport;
import com.accounting.merchandiseAccounting.model.Vehicle;
import com.accounting.merchandiseAccounting.repository.EmployeeRepository;
import com.accounting.merchandiseAccounting.repository.ProductRepository;
import com.accounting.merchandiseAccounting.repository.VehicleRepository;
import com.accounting.merchandiseAccounting.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Lazy
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Product saveProduct(Product product) {
        List<Vehicle> allAvailableVehicle = vehicleRepository.getAllAvailableVehicle();
        List<Employee> allAvailableEmployee = employeeRepository.getAllAvailableEmployees();
         allAvailableVehicle.stream()
                .filter(vehicle -> vehicle.getLiftingCapacity() >= product.getWeight())
                .findFirst().orElseThrow(() -> new CustomExceptionHandler("Not found vehicle with required" +
                        "lifting capacity"));
        allAvailableEmployee.stream().findAny().orElseThrow(() -> new CustomExceptionHandler("No employee found" +
                "for loading process"));

        Product productResponse = productRepository.saveProduct(product);
        return productResponse;
    }

    @Override
    public Product findProductById(long id) {
        return productRepository.findProductById(id);
    }

    @Override
    public List<Product> findProductByProductName(String productName) {
        return productRepository.findProductByProductName(productName);
    }

    @Override
    public int deleteProductById(long id) {
        return productRepository.deleteProductById(id);
    }

    @Override
    public List<Product> findAllProductsWhichIsNotProcessed() {
        List<Product> productList = productRepository.findAllProductsWhichIsNotProcessed();
        return productList;
    }

    @Override
    public void updateProductProceedStatusById(long id) {
        productRepository.updateProductProceedStatusById(id);
    }

    @Override
    public List<ProductForProceedDTO> getProductInfoForProceeding() {
        List<ProductForProceedDTO> productForProceedDTOS = productRepository.getProductInfoForProceeding();
        return productForProceedDTOS;
    }

    @Override
    public void updateProductLoadedByEmployee(ProductLoadedByEmployeeInfoDTO productForProceedDto) {
        productRepository.updateProductLoadedByEmployee(productForProceedDto);
    }

    @Override
    public ProductForProceedDTO getProductLoadedByEmployee(long INVNumber) {
        ProductForProceedDTO productForProceedDTO = productRepository.getProductLoadedByEmployee(INVNumber);
        return productForProceedDTO;
    }

    @Override
    public List<ProductForProceedDTO> getProductHistoryByEmployeeId(long employeeId) {
        return productRepository.getProductHistoryByEmployeeId(employeeId);
    }

    @Override
    public List<ProductForProceedDTO> getProductInfoByDate(Date shipment_date, boolean isPresent) {
        List<ProductForProceedDTO> productForProceedDTOList = productRepository.getProductInfoByDate(shipment_date, isPresent);
        return productForProceedDTOList;
    }

    @Override
    public void updateShipmentValueForSentBy(long employeeId, long INVNumber) {
        productRepository.updateShipmentValueForSentBy(employeeId, INVNumber);
    }

    @Override
    public void updateShipmentValueForIsPresent(long INVNumber, boolean isPresent) {
        productRepository.updateShipmentValueForIsPresent(INVNumber, isPresent);
    }

    @Override
    public ProductStorageReport getTotalAmountOfProducts() {
        int totalSpace = 100;
        int totalAmount = Math.toIntExact(productRepository.getTotalAmountOfProducts());
        int freeSpace = totalSpace - totalAmount;
        ProductStorageReport productStorageReport = new ProductStorageReport(totalAmount, freeSpace);
        return productStorageReport;
    }
}
