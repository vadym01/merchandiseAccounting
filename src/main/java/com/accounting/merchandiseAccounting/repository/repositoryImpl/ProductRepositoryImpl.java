package com.accounting.merchandiseAccounting.repository.repositoryImpl;

import com.accounting.merchandiseAccounting.DTO.ProductForProceedDTO;
import com.accounting.merchandiseAccounting.DTO.ProductLoadedByEmployeeInfoDTO;
import com.accounting.merchandiseAccounting.model.Employee;
import com.accounting.merchandiseAccounting.model.Product;
import com.accounting.merchandiseAccounting.repository.ProductRepository;
import com.accounting.merchandiseAccounting.service.EmployeeService;
import com.accounting.merchandiseAccounting.service.VehicleService;
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
            session.merge(product);
            session.getTransaction().commit();
            return product;
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @Transactional
    @Override
    public Product findProductById(long id) {
        try {
            Query query = session.createNamedQuery("findProductById").setParameter("id", id);
            Product product = (Product) query.getSingleResult();
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

    @Transactional
    @Override
    public void updateProductProceedStatusById(long id) {
        try {
            session.getTransaction().begin();
            Product product = session.find(Product.class, id);
            product.setProcessed(true);
            product.setArrivalDate(new Date());
            session.update(product);
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
            query.setParameter("loadedByEmployee", employee);
            query.setParameter("INVNumber", productForProceedDto.getProductId());
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Transactional
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
            logger.error(e.getMessage());
            return null;
        }
    }

    @Transactional
    @Override
    public List<ProductForProceedDTO> getProductHistoryByEmployeeId(long employeeId) {
        try {
            Employee employee = session.find(Employee.class, employeeId);
            List<ProductForProceedDTO> productForProceedDTOList = session.getNamedQuery("getProductHistoryByEmployeeId")
                    .setParameter("loadedByEmployee", employee)
                    .unwrap(Query.class)
                    .setResultTransformer(Transformers.aliasToBean(ProductForProceedDTO.class))
                    .getResultList();
            return productForProceedDTOList;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Transactional
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
            logger.error(e.getMessage());
            return null;
        }
    }

    @Transactional
    @Override
    public void updateShipmentValueForSentBy(long employeeId, long INVNumber) {
        try {
            session.getTransaction().begin();
            Query query = session.getNamedQuery("updateShipmentValueForSentBy");
            Employee employee = session.find(Employee.class, employeeId);
            query.setParameter("sentByEmployee", employee);
            query.setParameter("shipmentDate", new Date());
            query.setParameter("INVNumber", INVNumber);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public long getTotalAmountOfProducts() {
        long result = 0;
        try {
             result = (long) session.getNamedQuery("getTotalAmountOfProducts").uniqueResult();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public void updateShipmentValueForIsPresent(long INVNumber, boolean isPresent) {
        try {
            session.getTransaction().begin();
            session.getNamedQuery("updateShipmentValueForIsPresent")
                    .setParameter("isPresent", isPresent)
                    .setParameter("INVNumber", INVNumber)
                    .executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

//    @Transactional
//    @Override
//    public void updateProduct(Product product) {
//        session.update(product);
//    }
}
