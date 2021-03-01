package com.accounting.merchandiseAccounting.repository.repositoryImpl;

import com.accounting.merchandiseAccounting.model.Product;
import com.accounting.merchandiseAccounting.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

@Service
public class ProductRepositoryImpl implements ProductRepository {

    private final static Logger logger = LogManager.getLogger(EmployeeRepositoryImpl.class);

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private SessionFactory sessionFactory;
    private Session session;

    @PostConstruct
    public void init() {
        session = sessionFactory.openSession();
    }

    @Transactional
    @Override
    public void saveProduct(Product product) {
        session.save(product);
    }

    @Transactional
    @Override
    public Product findProductById(long id) {
        try {
            Query query = session.createNamedQuery("findProductById").setParameter("id",id);
            Product product = (Product) query.list().get(0);
            return product;
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @Transactional
    @Override
    public List<Product> findProductByProductName(String productName) {
        try {
            Query query = session.getNamedQuery("findProductByProductName")
                    .setParameter("productName",'%' + productName + '%');
            List<Product> productList = query.list();
            return productList;
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @Transactional
    @Override
    public int deleteProductById(long id) {
         try {
             Query query = session.createNamedQuery("deleteProductById").setParameter("id",id);
             Transaction transaction = session.beginTransaction();
             int num = query.executeUpdate();
             transaction.commit();
             return num;
         }catch (Exception e){
             logger.error(e.getMessage());
             return 0;
         }
    }
}
