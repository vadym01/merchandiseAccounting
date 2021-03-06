package com.accounting.merchandiseAccounting.controller;

import com.accounting.merchandiseAccounting.DTO.ProductForProceedDTO;
import com.accounting.merchandiseAccounting.DTO.ProductLoadedByEmployeeInfoDTO;
import com.accounting.merchandiseAccounting.exceptions.ResourceNotFoundException;
import com.accounting.merchandiseAccounting.model.Employee;
import com.accounting.merchandiseAccounting.model.Product;
import com.accounting.merchandiseAccounting.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam Optional<String> productName){
        List<Product> productList = productService.findProductByProductName(productName.orElse("_"));
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getProductByInv(@PathVariable("id") long id){
        Product product = productService.findProductById(id);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createProduct(@RequestBody Product product){
        productService.saveProduct(product);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteProductById(@PathVariable("id") long id) throws ResourceNotFoundException {
        int result = productService.deleteProductById(id);
        if(result == 0){
            throw new ResourceNotFoundException("Product with id: " + id + " not found");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity updateProductProceedStatusById(@PathVariable("id") long id){
        productService.updateProductProceedStatusById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/to/proceed")
    public ResponseEntity getProductListToProceed(){
        List<ProductForProceedDTO> productForProceedDTOS = productService.getProductInfoForProceeding();
        return new ResponseEntity(productForProceedDTOS,HttpStatus.OK);
    }

    @GetMapping("/to/proceed/{INVNumber}")
    public ResponseEntity getProductToProceed(@PathVariable("INVNumber") long INVNumber){
        ProductForProceedDTO productForProceedDTO = productService.getProductLoadedByEmployee(INVNumber);
        return new ResponseEntity(productForProceedDTO,HttpStatus.OK);
    }

    @PatchMapping("update/begin/proceed")
    public ResponseEntity updateProductLoadedBy(@RequestBody ProductLoadedByEmployeeInfoDTO productInfoDto){
        productService.updateProductLoadedByEmployee(productInfoDto);
        return new ResponseEntity(HttpStatus.OK);
    }

}
