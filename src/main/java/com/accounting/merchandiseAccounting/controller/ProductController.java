package com.accounting.merchandiseAccounting.controller;

import com.accounting.merchandiseAccounting.exceptions.ResourceNotFoundException;
import com.accounting.merchandiseAccounting.model.Product;
import com.accounting.merchandiseAccounting.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> productList = productService.getAllProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id){
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
}
