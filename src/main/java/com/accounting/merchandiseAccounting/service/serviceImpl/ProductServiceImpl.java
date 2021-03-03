package com.accounting.merchandiseAccounting.service.serviceImpl;

import com.accounting.merchandiseAccounting.model.Product;
import com.accounting.merchandiseAccounting.repository.ProductRepository;
import com.accounting.merchandiseAccounting.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void saveProduct(Product product) {
        productRepository.saveProduct(product);
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
    public List<Product> findAllProductsWitchIsNotProcessed() {
        List<Product> productList = productRepository.findAllProductsWitchIsNotProcessed();
        return productList;
    }

    @Override
    public void updateProductProceedStatusById(long id) {
        productRepository.updateProductProceedStatusById(id);
    }
}
