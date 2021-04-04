package com.accounting.merchandiseAccounting.repository.crudRepository;

import com.accounting.merchandiseAccounting.model.Product;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface CrudProvider<T> {

    void setClassInstance(Class<T> classInstance);

    T findOneById(long id);

    List<T> findAll();

    T save(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteById(final long entityId);
}
