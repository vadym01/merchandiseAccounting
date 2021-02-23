package com.accounting.merchandiseAccounting.service;

import com.accounting.merchandiseAccounting.model.Product;

import java.util.List;

public interface ProductService {
    void saveProduct(Product product);
    Product findProductById(long id);
    List<Product> getAllProducts();
    int deleteProductById(long id);
}
