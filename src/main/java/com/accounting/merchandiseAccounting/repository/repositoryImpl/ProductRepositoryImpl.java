package com.accounting.merchandiseAccounting.repository.repositoryImpl;

import com.accounting.merchandiseAccounting.DTO.ProductForProceedDTO;
import com.accounting.merchandiseAccounting.DTO.ProductLoadedByEmployeeInfoDTO;
import com.accounting.merchandiseAccounting.model.Employee;
import com.accounting.merchandiseAccounting.model.Equipment;
import com.accounting.merchandiseAccounting.model.Product;
import com.accounting.merchandiseAccounting.repository.ProductRepository;
import com.accounting.merchandiseAccounting.service.EmployeeService;
import com.accounting.merchandiseAccounting.service.EquipmentService;
import com.accounting.merchandiseAccounting.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.xml.transform.Transformer;
import java.util.List;

@Service
public class ProductRepositoryImpl implements ProductRepository {

    private final static Logger logger = LogManager.getLogger(EmployeeRepositoryImpl.class);

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private ProductService productService;
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
            Query query = session.createNamedQuery("findProductById").setParameter("id", id);
            Product product = (Product) query.list().get(0);
            return product;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Transactional
    @Override
    public List<Product> findProductByProductName(String productName) {
        try {
            Query query = session.getNamedQuery("findProductByProductName")
                    .setParameter("productName", '%' + productName + '%');
            List<Product> productList = query.list();
            return productList;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Transactional
    @Override
    public int deleteProductById(long id) {
        try {
            Query query = session.createNamedQuery("deleteProductById").setParameter("id", id);
            Transaction transaction = session.beginTransaction();
            int num = query.executeUpdate();
            transaction.commit();
            return num;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return 0;
        }
    }

    @Transactional
    @Override
    public List<Product> findAllProductsWhichIsNotProcessed() {
        try {
            Query query = session.getNamedQuery("findAllProductsWhichIsNotProcessed");
            List<Product> productList = query.list();
            return productList;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void updateProductProceedStatusById(long id) {
        try {
            session.getTransaction().begin();
            Product product = session.find(Product.class, id);
            product.setProcessed(!product.isProcessed());
            session.save(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Transactional
    @Override
    public List<ProductForProceedDTO> getProductInfoForProceeding() {
        try {
            List<ProductForProceedDTO> product = session.getNamedQuery("getProductInfoForProceeding").unwrap(Query.class).
                    setResultTransformer(Transformers.aliasToBean(ProductForProceedDTO.class))
                    .getResultList();
            return product;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Transactional
    @Override
    public void updateProductLoadedByEmployee(ProductLoadedByEmployeeInfoDTO productForProceedDto) {
        try {
            session.getTransaction().begin();
            Query query = session.getNamedQuery("updateProductLoadedByEmployee");
            Employee employee = session.find(Employee.class, productForProceedDto.getCurrentEmployeeId());
            query.setParameter("loadedByEmployee",employee);
            query.setParameter("INVNumber", productForProceedDto.getProductId());
          query.executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Transactional
    @Override
    public ProductForProceedDTO getProductLoadedByEmployee(long INVNumber) {
          ProductForProceedDTO product = (ProductForProceedDTO) session.getNamedQuery("getProductLoadedByEmployee")
                .setParameter("INVNumber", INVNumber)
                .unwrap(Query.class)
                .setResultTransformer(Transformers.aliasToBean(ProductForProceedDTO.class))
                .getResultList().get(0);
          return product;
    }
}
