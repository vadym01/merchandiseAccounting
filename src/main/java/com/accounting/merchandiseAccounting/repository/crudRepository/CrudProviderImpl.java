package com.accounting.merchandiseAccounting.repository.crudRepository;

import com.accounting.merchandiseAccounting.exceptions.BadRequestExceptionHandler;
import com.accounting.merchandiseAccounting.exceptions.IdNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CrudProviderImpl<T> implements CrudProvider<T> {
    private Class<T> classInstance;

    @Autowired
    private SessionFactory sessionFactory;

    private Session session;

    @PostConstruct
    public void init() {
        session = sessionFactory.openSession();
    }

    @PersistenceContext
    EntityManager entityManager;


    public void setClassInstance(Class<T> classInstance) {
        this.classInstance = classInstance;
    }

    public T findOneById(long id) {
        try {
            return session.find(classInstance, id);
        } catch (NoResultException e) {
            throw new IdNotFoundException("No entity was found with id: " + id);
        } catch (Exception e) {
            throw new BadRequestExceptionHandler(e.getMessage());
        }
    }


    public List<T> findAll() {
        List<T> findAll = new ArrayList<>();
                findAll.addAll(entityManager.createQuery("FROM " + classInstance.getName()).getResultList());
        return findAll;
    }

    @Transactional
    public T save(T entity) {
        try {
            session.getTransaction().begin();
            session.merge(entity);
            session.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeException) {
                throw new BadRequestExceptionHandler(runtimeException.getMessage());
            }
            throw new BadRequestExceptionHandler(e.getMessage());
        }
    }

    @Transactional
    public T update(T entity) {
        try {
            session.getTransaction().begin();
            session.update(entity);
            session.getTransaction().commit();
            return null;
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeException) {
                throw new BadRequestExceptionHandler(runtimeException.getMessage());
            }
            throw new BadRequestExceptionHandler(e.getMessage());
        }
    }

    public void delete(T entity) {
        try {
            session.getTransaction().begin();
            session.delete(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeException) {
                throw new BadRequestExceptionHandler(runtimeException.getMessage());
            }
            throw new BadRequestExceptionHandler(e.getMessage());
        }
    }

    public void deleteById(long id) {
        T entity = findOneById(id);
        delete(entity);
    }
}
