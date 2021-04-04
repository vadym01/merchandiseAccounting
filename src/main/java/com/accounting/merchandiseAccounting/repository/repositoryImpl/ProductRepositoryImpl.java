package com.accounting.merchandiseAccounting.repository.repositoryImpl;

import com.accounting.merchandiseAccounting.dto.ProductForProceedDTO;
import com.accounting.merchandiseAccounting.dto.ProductLoadedByEmployeeInfoDTO;
import com.accounting.merchandiseAccounting.exceptions.BadRequestExceptionHandler;
import com.accounting.merchandiseAccounting.exceptions.IdNotFoundException;
import com.accounting.merchandiseAccounting.model.Employee;
import com.accounting.merchandiseAccounting.model.Product;
import com.accounting.merchandiseAccounting.repository.ProductRepository;
import com.accounting.merchandiseAccounting.repository.crudRepository.CrudProvider;
import com.accounting.merchandiseAccounting.service.EmployeeService;
import com.accounting.merchandiseAccounting.service.VehicleService;
import com.accounting.merchandiseAccounting.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
    public Product saveOrUpdateProduct(Product product) {
        System.out.println(product);
        try {
            if (product.getINVNumber() == 0) {
                session.getTransaction().begin();
                session.save(product);
                session.getTransaction().commit();
                return product;
            } else {
                Query query = session.getNamedQuery("updateProduct")
                        .setParameter("productName", product.getProductName())
                        .setParameter("description", product.getDescription())
                        .setParameter("volume", product.getVolume())
                        .setParameter("weight", product.getWeight())
                        .setParameter("sender", product.getSender())
                        .setParameter("receiver", product.getReceiver())
                        .setParameter("receiptDate", product.getReceiptDate())
                        .setParameter("scheduledShipmentDate", product.getScheduledShipmentDate())
                        .setParameter("isPresent", product.isPresent())
                        .setParameter("isProcessed", product.isProcessed())
                        .setParameter("INVNumber", product.getINVNumber());
                session.getTransaction().begin();
                long inv = query.executeUpdate();
                session.getTransaction().commit();
                session.get(Product.class, inv);
                return product;
            }
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeException) {
                throw new BadRequestExceptionHandler(e.getMessage());
            }
            throw new BadRequestExceptionHandler(e.getMessage());
        }

    }


//    @Override
//    public Product findProductById(long id) {
//        try {
//            Query query = session.createNamedQuery("findProductById").setParameter("id", id);
//            Product product = (Product) query.getSingleResult();
//            return product;
//        } catch (NoResultException e) {
//            throw new IdNotFoundException("No product was found with INV: " + id);
//        } catch (Exception e) {
//            throw new BadRequestExceptionHandler(e.getMessage());
//        }
//    }

    @Override
    public List<Product> findProductsByProductName(String productName) {
        try {
            Query query = session.getNamedQuery("findProductsByProductName")
                    .setParameter("productName", '%' + productName + '%');
            List<Product> productList = query.list();
            return productList;
        } catch (Exception e) {
            throw new BadRequestExceptionHandler(e.getMessage());
        }
    }

//    @Override
//    public int deleteProductById(long id) {
//        try {
//            session.beginTransaction();
//            int result = session.getNamedQuery("deleteProductById")
//                    .setParameter("id", id)
//                    .executeUpdate();
//            session.getTransaction().commit();
//            return result;
//        } catch (NoResultException e) {
//            throw new IdNotFoundException("No product was found with INV: "  + id);
//        } catch (Exception e){
//            try {
//                session.getTransaction().rollback();
//            } catch (RuntimeException runtimeException) {
//                runtimeException.printStackTrace();
//            }
//            throw new BadRequestExceptionHandler(e.getMessage());
//        }
//    }


    @Override
    public List<Product> findAllProductsWhichIsNotProcessed() {
        try {
            Query query = session.getNamedQuery("findAllProductsWhichIsNotProcessed");
            List<Product> productList = query.list();
            return productList;
        } catch (Exception e) {
            throw new BadRequestExceptionHandler(e.getMessage());
        }
    }

//    @Override
//    public void updateProductProceedStatusById(long id) {
////        try {
////            Product product = (Product) session.getNamedQuery("findProductById").setParameter("id", id).getSingleResult();
////            product.setProcessed(true);
////            product.setArrivalDate(new Date());
////            session.getTransaction().begin();
////            session.update(product);
////            session.getTransaction().commit();
////        } catch (NoResultException e) {
////            throw new IdNotFoundException("No product was found with INV: " + id);
////        } catch (Exception e) {
////            try {
////                session.getTransaction().rollback();
////            } catch (RuntimeException runtimeException) {
////                throw new BadRequestExceptionHandler(runtimeException.getMessage());
////            }
////            throw new BadRequestExceptionHandler(e.getMessage());
////        }
//    }

    @Override
    public List<ProductForProceedDTO> getProductInfoForProceeding() {
        try {
            List<ProductForProceedDTO> product = session.getNamedQuery("getProductInfoForProceeding").unwrap(Query.class).
                    setResultTransformer(Transformers.aliasToBean(ProductForProceedDTO.class))
                    .getResultList();
            return product;
        } catch (Exception e) {
            throw new BadRequestExceptionHandler(e.getMessage());
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
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeException) {
                throw new BadRequestExceptionHandler(runtimeException.getMessage());
            }
            throw new BadRequestExceptionHandler(e.getMessage());
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
        } catch (NoResultException e) {
            throw new IdNotFoundException("No product was found with INV: " + INVNumber);
        } catch (Exception e) {
            throw new BadRequestExceptionHandler(e.getMessage());
        }
    }

    @Override
    public List<ProductForProceedDTO> getProductHistoryByEmployeeId(long employeeId) {
        try {
            Employee employee = session.get(Employee.class, employeeId);
            List<ProductForProceedDTO> productForProceedDTOList = session.getNamedQuery("getProductHistoryByEmployeeId")
                    .setParameter("loadedByEmployee", employee)
                    .unwrap(Query.class)
                    .setResultTransformer(Transformers.aliasToBean(ProductForProceedDTO.class))
                    .getResultList();
            return productForProceedDTOList;
        } catch (NoResultException e) {
            throw new IdNotFoundException("No employee was found with INV: " + employeeId);
        } catch (Exception e) {
            throw new BadRequestExceptionHandler(e.getMessage());
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
        } catch (NoResultException e) {
            throw new IdNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new BadRequestExceptionHandler(e.getMessage());
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
        } catch (NoResultException e) {
            throw new IdNotFoundException("No product was found with INV: " + INVNumber);
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeException) {
                runtimeException.printStackTrace();
            }
            e.printStackTrace();
            throw new BadRequestExceptionHandler(e.getMessage());
        }
    }

    @Override
    public long getTotalAmountOfProducts() {
        try {
            long result = (long) session.getNamedQuery("getTotalAmountOfProducts").uniqueResult();
            return result;
        } catch (Exception e) {
            throw new BadRequestExceptionHandler(e.getMessage());
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
        } catch (NoResultException e) {
            throw new IdNotFoundException("No product was found with id: " + INVNumber);
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeException) {
                runtimeException.printStackTrace();
            }
            e.printStackTrace();
            throw new BadRequestExceptionHandler(e.getMessage());
        }
    }


}
