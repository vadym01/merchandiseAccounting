package com.accounting.merchandiseAccounting.repository.repositoryImpl;

import com.accounting.merchandiseAccounting.model.*;
import com.accounting.merchandiseAccounting.repository.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


import com.accounting.merchandiseAccounting.model.Employee;
import com.accounting.merchandiseAccounting.repository.EmployeeRepository;
import com.accounting.merchandiseAccounting.repository.IncidentRepository;

import static org.hamcrest.MatcherAssert.*;

import java.util.ArrayList;


import static org.hamcrest.CoreMatchers.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductRepositoryImplTest {


    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IncidentRepository incidentRepository;


    @Autowired
    private EntityManager entityManager;
    Session session;


    private Employee employee;
    private Product product;
    private Product notProcessedProduct;
    private Incident incident;
    private List<Employee> employeeList;
    private List<Product> productList;

    private Date getDate(String date) {
        Date dateFromString;
        try {
            dateFromString = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            return dateFromString;
        } catch (ParseException e) {
            return null;
        }
    }

    @BeforeEach
    public void init() {
        employee = new Employee("Jhan", "Stewarts", "Anatolievich",true, getDate("1993-12-12"));
        product = new Product("Computer", "Laptop", 23.4, 12.3, "Jordan Albertson", "Georg Allford", new Date(), new Date(), new Date(),
                true, true, new Date());
        productList = new ArrayList<Product>(Arrays.asList(
                new Product("CPU", "accessories", 1.1, 0.3, "Oleg Hit", "Georg Allford", new Date(), new Date(), new Date(),
                        true, false, new Date()),
                new Product("Monitor", "accessories", 30.3, 1.2, "Georg Albertson", "Georg Allford", new Date(), new Date(), new Date(),
                        false, true, new Date())
        ));

        incident = new Incident("testIncidentForEmployee", new Date(), employee);
        employeeList = Arrays.asList(
                new Employee("Jordan", "Albertson", "BAtikovitch",true, getDate("1993-05-18")),
                new Employee("Georg", "Allford", "BAtikovitch",true, getDate("1993-05-18")),
                new Employee("Jonny", "Cameroon", "BAtikovitch",true, getDate("2001-04-11")),
                new Employee("Oleg", "Adrian", "BAtikovitch",true, getDate("2020-11-16")),
                new Employee("Adolf", "Hit", "Germ",true, getDate("1993-12-12")));


        session = sessionFactory.openSession();
        session.getTransaction().begin();
        Query productQuery = session.createQuery("Delete from Product");
        Query employeeQuery = session.createQuery("Delete from Employee");
        Query incidentsQuery = session.createQuery("Delete from Incidents");


        productQuery.executeUpdate();
        incidentsQuery.executeUpdate();
        employeeQuery.executeUpdate();
        session.getTransaction().commit();
    }


    @Test
    void saveProduct() {
        session.beginTransaction();
        long testProduct = (long) session.save(product);
        session.getTransaction().commit();
        Query query = session.getNamedQuery("findProductById")
                .setParameter("id", testProduct);
        assertNotNull(query.getSingleResult());
    }

    @Test
    void findProductById() {
        String productName = this.product.getProductName();
        List<Product> productList = session.getNamedQuery("getAllProducts")
                .getResultList();
        assertTrue(productList.size() == 0);
        session.beginTransaction();
        long testProductId = (long) session.save(product);
        session.getTransaction().commit();
        List<Product> productListAfterSaving = session.getNamedQuery("getAllProducts").getResultList();
        assertTrue(productListAfterSaving.size() == 1);
        Product product = productRepository.findProductById(testProductId);
        assertThat(productName, is(product.getProductName()));
    }

    @Test
    void findProductByProductName() {
        List<Product> productList = session.getNamedQuery("getAllProducts").getResultList();
        assertTrue(productList.size() == 0);
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
        List<Product> productByProductName = productRepository.findProductByProductName(product.getProductName());
        List<Product> productListAfterSaving = session.getNamedQuery("getAllProducts").getResultList();
        assertTrue(productListAfterSaving.size() != 0);
        assertEquals(productListAfterSaving.get(0), product);
    }

    @Test
    void deleteProductById() {
        long productId = (long) session.save(product);
        Product productById = session.find(Product.class, productId);
        assertThat(productById, is(product));
        productRepository.deleteProductById(productId);
        System.out.println(productId);
        List<Product> productList = session.getNamedQuery("getAllProducts").getResultList();
        assertThat(productList.size(), is(0));
    }

    @Test
    void findAllProductsWhichIsNotProcessed() {
        session.beginTransaction();
        productList.forEach(p -> {
            session.save(p);
        });
        session.getTransaction().commit();
        List<Product> productListNotProcessed = productRepository.findAllProductsWhichIsNotProcessed();
        assertTrue(productListNotProcessed.size() == 1);
    }
//
//    @Test
//    void updateProductProceedStatusById() {
//        product.setProcessed(false);
//        product.setArrivalDate(null);
//        session.beginTransaction();
//        long productId = (long) session.save(product);
//        session.getTransaction().commit();
//        Product productById = session.find(Product.class, productId);
//        System.out.println(productById);
//        assertThat(productById.isProcessed(), is(false));
//        assertThat(productById.getArrivalDate(), is(null));
////        productRepository.updateProductProceedStatusById(productId);
////        Product productAfterUpdate = (Product) session.getNamedQuery("findProductById")
////        .setParameter("id", productId);
////        assertThat(productAfterUpdate.isProcessed(), is(true));
////        assertThat(productAfterUpdate.getArrivalDate(), not(null));
//    }

    @Test
    void getProductInfoForProceeding() {

    }

    @Test
    void updateProductLoadedByEmployee() {
    }

    @Test
    void getProductLoadedByEmployee() {
    }

    @Test
    void getProductHistoryByEmployeeId() {
    }

    @Test
    void getProductInfoByDate() {
    }

    @Test
    void updateShipmentValueForSentBy() {
    }

    @Test
    void getTotalAmountOfProducts() {
    }

    @Test
    void updateShipmentValueForIsPresent() {
    }

}