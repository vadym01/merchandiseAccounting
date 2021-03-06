package com.accounting.merchandiseAccounting.controller;

import com.accounting.merchandiseAccounting.dto.ProductForProceedDTO;
import com.accounting.merchandiseAccounting.dto.ProductLoadedByEmployeeInfoDTO;
import com.accounting.merchandiseAccounting.dto.ProductReportDto;
import com.accounting.merchandiseAccounting.exceptions.IdNotFoundException;
import com.accounting.merchandiseAccounting.model.Product;
import com.accounting.merchandiseAccounting.repository.ProductRepository;
import com.accounting.merchandiseAccounting.service.ProductService;
import com.accounting.merchandiseAccounting.validationService.MapValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private MapValidationService mapValidationService;

    @Autowired
    private ProductRepository productRepository;

//    @GetMapping
//    public ResponseEntity<List<Product>> findProductsByProductName(@RequestParam Optional<String> productName) {
//        List<Product> productList = productService.findProductsByProductName(productName.orElse("_"));
//        return new ResponseEntity<>(productList, HttpStatus.OK);
//    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = productService.findAllProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getProductByInv(@PathVariable("id") long id) {
        Product product = productService.findProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveProduct(@Validated @RequestBody Product product, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = mapValidationService.mapValidationService(bindingResult);
        if(errorMap != null) return errorMap;
        Product productResponse = productService.saveProduct(product);
        return new ResponseEntity(productResponse,HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteProductById(@PathVariable("id") long id) {
        int result = productService.deleteProductById(id);
        if(result == 0){
            throw new IdNotFoundException("Product with selected id: " + id + " does not exist");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/submit/proceeded/{id}")
    public ResponseEntity updateProductProceedStatusById(@PathVariable("id") long id) {
        productService.updateProductProceedStatusById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/to/proceed")
    public ResponseEntity getProductListToProceed() {
        List<ProductForProceedDTO> productForProceedDTOS = productService.getProductInfoForProceeding();
        return new ResponseEntity(productForProceedDTOS, HttpStatus.OK);
    }

    @GetMapping("/to/proceed/{INVNumber}")
    public ResponseEntity getProductToProceed(@PathVariable("INVNumber") long INVNumber) {
        ProductForProceedDTO productForProceedDTO = productService.getProductLoadedByEmployee(INVNumber);
        return new ResponseEntity(productForProceedDTO, HttpStatus.OK);
    }

    @PatchMapping("update/begin/proceed")
    public ResponseEntity updateProductLoadedBy(@RequestBody ProductLoadedByEmployeeInfoDTO productInfoDto) {
        productService.updateProductLoadedByEmployee(productInfoDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("history/of/{employeeId}")
    public ResponseEntity getProductHistoryByEmployee(@PathVariable("employeeId") long employeeId) {
        List<ProductForProceedDTO> productHistory = productService.getProductHistoryByEmployeeId(employeeId);
        return new ResponseEntity(productHistory, HttpStatus.OK);
    }

    @GetMapping("history/by/date/{shipment_date}/{isPresent}")
    public ResponseEntity getProductListInfoByDate(@PathVariable("shipment_date") String date,
                                                   @PathVariable("isPresent") boolean isPresent) {
        List<ProductForProceedDTO> productForProceedDTOList = new ArrayList<>();
        try {
            Date conditionDate = new SimpleDateFormat("yyyy-mm-dd").parse(date);
            productForProceedDTOList = productService.getProductInfoByDate(conditionDate, isPresent);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(productForProceedDTOList, HttpStatus.OK);
    }

    @PatchMapping("update/begin/shipment/{employeeId}/{INVNumber}")
    public ResponseEntity updateShipmentValueForSentBy(@PathVariable("employeeId") long employeeId,
                                                       @PathVariable("INVNumber") long INVNumber) {
        productService.updateShipmentValueForSentBy(employeeId, INVNumber);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("update/confirm/shipment/{INVNumber}/{isPresent}")
    public ResponseEntity updateShipmentValueForIsPresent(@PathVariable("INVNumber") long INVNumber,
                                                          @PathVariable("isPresent") boolean isPresent) {
        productService.updateShipmentValueForIsPresent(INVNumber, isPresent);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("total")
    public ResponseEntity getTotalAmountOfProducts(){
//        ProductStorageReport productStorageReport = productService.getTotalAmountOfProducts();
        ProductReportDto productStorageReport = productService.getTotalAmountOfProducts();
        return new ResponseEntity(productStorageReport,HttpStatus.OK);
    }
}
