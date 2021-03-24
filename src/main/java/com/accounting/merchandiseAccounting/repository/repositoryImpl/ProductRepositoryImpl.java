package com.accounting.merchandiseAccounting.repository.repositoryImpl;

import com.accounting.merchandiseAccounting.DTO.ProductForProceedDTO;
import com.accounting.merchandiseAccounting.DTO.ProductLoadedByEmployeeInfoDTO;
import com.accounting.merchandiseAccounting.exceptions.customExceptionHandler.IdNotFoundException;
import com.accounting.merchandiseAccounting.model.Employee;
import com.accounting.merchandiseAccounting.model.Product;
import com.accounting.merchandiseAccounting.repository.ProductRepository;
import com.accounting.merchandiseAccounting.service.EmployeeService;
import com.accounting.merchandiseAccounting.service.VehicleService;
import com.accounting.merchandiseAccounting.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Date;
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
    private VehicleService vehicleService;
    @Autowired
    private ProductService productService;
    private Session session;

    @PostConstruct
    public void init() {
        session = sessionFactory.openSession();
    }

    @Override
    public Product saveProduct(Product product) {
        try {
            session.getTransaction().begin();
            session.save(product);
            session.getTransaction().commit();
            return product;
        } catch (HibernateException hibernateException) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeException) {
                runtimeException.printStackTrace();
            }
            hibernateException.printStackTrace();
            return null;
        }
    }

    @Override
    public Product findProductById(long id) {
        try {
            Query query = session.createNamedQuery("findProductById").setParameter("id", id);
            Product product = (Product) query.getSingleResult();
            return product;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IdNotFoundException("Product with selected id: " + id + " does not exist");
        }
    }

    @Override
    public List<Product> findProductByProductName(String productName) {
        try {
            Query query = session.getNamedQuery("findProductByProductName")
                    .setParameter("productName", '%' + productName + '%');
            List<Product> productList = query.list();
            return productList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HibernateException("Database connection error");
        }
    }

    @Override
    public int deleteProductById(long id) {
        try {
            session.beginTransaction();
            int result = session.getNamedQuery("deleteProductById")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
            return result;
        } catch (HibernateException e) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeException) {
                runtimeException.printStackTrace();
            }
            return 0;
        }
    }

    @Override
    public List<Product> findAllProductsWhichIsNotProcessed() {
        try {
            Query query = session.getNamedQuery("findAllProductsWhichIsNotProcessed");
            List<Product> productList = query.list();
            return productList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HibernateException("Database connection error");
        }
    }

    @Override
    public void updateProductProceedStatusById(long id) {
        try {
            Product product = (Product) session.getNamedQuery("findProductById").setParameter("id", id).getSingleResult();
            product.setProcessed(true);
            product.setArrivalDate(new Date());
            session.getTransaction().begin();
            session.update(product);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeException) {
                runtimeException.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<ProductForProceedDTO> getProductInfoForProceeding() {
        try {
            List<ProductForProceedDTO> product = session.getNamedQuery("getProductInfoForProceeding").unwrap(Query.class).
                    setResultTransformer(Transformers.aliasToBean(ProductForProceedDTO.class))
                    .getResultList();
            return product;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HibernateException("Database connection error");
        }
    }

    @Override
    public void updateProductLoadedByEmployee(ProductLoadedByEmployeeInfoDTO productForProceedDto) {
        try {
            Query query = session.getNamedQuery("updateProductLoadedByEmployee");
            Employee employee = session.find(Employee.class, productForProceedDto.getCurrentEmployeeId());
            query.setParameter("loadedByEmployee", employee);
            query.setParameter("INVNumber", productForProceedDto.getProductId());
            session.beginTransaction();
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeException) {
                runtimeException.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public ProductForProceedDTO getProductLoadedByEmployee(long INVNumber) {
        try {
            ProductForProceedDTO product = (ProductForProceedDTO) session.getNamedQuery("getProductLoadedByEmployee")
                    .setParameter("INVNumber", INVNumber)
                    .unwrap(Query.class)
                    .setResultTransformer(Transformers.aliasToBean(ProductForProceedDTO.class))
                    .getResultList().get(0);
            return product;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IdNotFoundException("product with id: " + INVNumber + " does not exist");
        }
    }

    @Override
    public List<ProductForProceedDTO> getProductHistoryByEmployeeId(long employeeId) {
        try {
            Employee employee = (Employee) session.getNamedQuery("getEmployeeById")
                    .setParameter("id", employeeId);
            List<ProductForProceedDTO> productForProceedDTOList = session.getNamedQuery("getProductHistoryByEmployeeId")
                    .setParameter("loadedByEmployee", employee)
                    .unwrap(Query.class)
                    .setResultTransformer(Transformers.aliasToBean(ProductForProceedDTO.class))
                    .getResultList();
            return productForProceedDTOList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IdNotFoundException("Employee with id: " + employeeId + " does not exist");
        }
    }

    @Override
    public List<ProductForProceedDTO> getProductInfoByDate(Date shipment_date, boolean isPresent) {
        try {
            List<ProductForProceedDTO> productForProceedDTOList = session.getNamedQuery("getProductInfoByDate")
                    .setParameter("scheduledShipmentDate", new Date())
                    .setParameter("isPresent", true)
                    .unwrap(Query.class)
                    .setResultTransformer(Transformers.aliasToBean(ProductForProceedDTO.class))
                    .getResultList();
            return productForProceedDTOList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IdNotFoundException("Product with date: " + shipment_date + " does not exist");
        }
    }

    @Override
    public void updateShipmentValueForSentBy(long employeeId, long INVNumber) {
        try {
            Query query = session.getNamedQuery("updateShipmentValueForSentBy");
            Employee employee = session.find(Employee.class, employeeId);
            query.setParameter("sentByEmployee", employee);
            query.setParameter("shipmentDate", new Date());
            query.setParameter("INVNumber", INVNumber);
            session.beginTransaction();
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException hibernateException) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeException) {
                runtimeException.printStackTrace();
            }
            hibernateException.printStackTrace();
        }
    }

    @Override
    public long getTotalAmountOfProducts() {
        try {
            long result = (long) session.getNamedQuery("getTotalAmountOfProducts").uniqueResult();
//            return result;
            throw new HibernateException("Database connection error");
        } catch (Exception e) {
            e.printStackTrace();
//            throw new HibernateException("Database connection error");
            return 0;
        }
    }

    @Override
    public void updateShipmentValueForIsPresent(long INVNumber, boolean isPresent) {
        try {
            session.getTransaction().begin();
            session.getNamedQuery("updateShipmentValueForIsPresent")
                    .setParameter("isPresent", isPresent)
                    .setParameter("INVNumber", INVNumber)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException hibernateException) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeException) {
                runtimeException.printStackTrace();
            }
            hibernateException.printStackTrace();
        }
    }

}
