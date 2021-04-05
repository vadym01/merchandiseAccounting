package com.accounting.merchandiseAccounting.service.serviceImpl;

import com.accounting.merchandiseAccounting.dto.ProductForProceedDTO;
import com.accounting.merchandiseAccounting.dto.ProductLoadedByEmployeeInfoDTO;
import com.accounting.merchandiseAccounting.exceptions.BadRequestExceptionHandler;
import com.accounting.merchandiseAccounting.model.Employee;
import com.accounting.merchandiseAccounting.model.Product;
import com.accounting.merchandiseAccounting.dto.ProductStorageReport;
import com.accounting.merchandiseAccounting.model.Vehicle;
import com.accounting.merchandiseAccounting.repository.EmployeeRepository;
import com.accounting.merchandiseAccounting.repository.ProductRepository;
import com.accounting.merchandiseAccounting.repository.VehicleRepository;
import com.accounting.merchandiseAccounting.repository.crudRepository.CrudProvider;
import com.accounting.merchandiseAccounting.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    private CrudProvider<Product> crudProvider;


    @Autowired
    public void setCrudProvider(CrudProvider<Product> crudProvider) {
        this.crudProvider = crudProvider;
        this.crudProvider.setClassInstance(Product.class);
    }

    @Override
    public Product saveProduct(Product product) {
        List<Vehicle> allAvailableVehicle = new ArrayList<>();
        List<Employee> allAvailableEmployee = new ArrayList<>();
        allAvailableVehicle.addAll(vehicleRepository.getAllAvailableVehicles());
        allAvailableEmployee.addAll(employeeRepository.getAllAvailableEmployees());
        allAvailableVehicle.stream()
                .filter(vehicle -> vehicle.getLiftingCapacity() >= product.getWeight())
                .findFirst().orElseThrow(() -> new BadRequestExceptionHandler("Not found vehicle with required" +
                "lifting capacity"));
        allAvailableEmployee.stream().findAny().orElseThrow(() -> new BadRequestExceptionHandler("No employee found" +
                "for loading process"));

        Product productResponse = productRepository.saveOrUpdateProduct(product);
        return productResponse;
    }

    @Override
    public Product findProductById(long id) {
        Product product = crudProvider.findOneById(id);
        return product;
    }

//    @Override
//    public List<Product> findProductsByProductName(String productName) {
//        return productRepository.findProductsByProductName(productName);
//    }

    @Override
    public int deleteProductById(long id) {
        crudProvider.deleteById(id);
        return 1;
    }

//    @Override
//    public List<Product> findAllProductsWhichIsNotProcessed() {
//        List<Product> productList = productRepository.findAllProductsWhichIsNotProcessed();
//        return productList;
//    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.addAll(crudProvider.findAll());
        return productList;
    }

    @Override
    public void updateProductProceedStatusById(long id) {
        Product product = crudProvider.findOneById(id);
        product.setProcessed(true);
        product.setArrivalDate(new Date());
        crudProvider.update(product);
    }

    @Override
    public List<ProductForProceedDTO> getProductInfoForProceeding() {
        List<ProductForProceedDTO> productForProceedDTOS = new ArrayList<>();
        productForProceedDTOS.addAll(productRepository.getProductsInfoForProceeding());
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
        List<ProductForProceedDTO> productForProceedDTOS = new ArrayList<>();
        productForProceedDTOS.addAll(productRepository.getProductsHistoryByEmployeeId(employeeId));
        return productForProceedDTOS;
    }

    @Override
    public List<ProductForProceedDTO> getProductInfoByDate(Date shipment_date, boolean isPresent) {
        List<ProductForProceedDTO> productForProceedDTOList = new ArrayList<>();
        productForProceedDTOList.addAll(productRepository.getProductsInfoByDate(shipment_date, isPresent));
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
