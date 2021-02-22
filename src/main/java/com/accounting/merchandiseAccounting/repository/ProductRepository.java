package com.accounting.merchandiseAccounting.repository;

import com.accounting.merchandiseAccounting.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
