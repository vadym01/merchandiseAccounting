package com.accounting.merchandiseAccounting.service;

import com.accounting.merchandiseAccounting.model.Product;

import java.util.List;

public interface ProductService {
    void saveProduct(Product product);
    Product findProductById(long id);
    List<Product> findProductByProductName(String productName);
    int deleteProductById(long id);
}
