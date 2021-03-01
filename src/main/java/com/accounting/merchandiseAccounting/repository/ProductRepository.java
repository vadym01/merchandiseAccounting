package com.accounting.merchandiseAccounting.repository;

import com.accounting.merchandiseAccounting.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository{
    void saveProduct(Product product);
    Product findProductById(long id);
    List<Product> findProductByProductName(String productName);
    int deleteProductById(long id);
}
