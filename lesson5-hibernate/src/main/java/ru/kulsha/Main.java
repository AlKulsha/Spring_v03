package ru.kulsha;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;
import ru.kulsha.model.Contact;
import ru.kulsha.model.ContactType;
import ru.kulsha.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        Contact mobile = new Contact(ContactType.MOBILE_PHONE, "12556");
//        Contact email = new Contact(ContactType.HOME_EMAIL,   "fff@mail.ru");
//        List<Contact> contactList = Arrays.asList(mobile, email);
//
//        Product product = new Product("prod1", 1100.0,
//                contactList,"pass1");

//        entityManager.getTransaction().begin();
//
//        contactList.forEach(contact -> contact.setProduct(product));
//
//        entityManager.persist(product);

//        List<Product> products = entityManager.createNamedQuery("findAllProducts", Product.class)
//                .getResultList();
//
//        List<Contact> contacts = products.get(0).getContacts();

//
//        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
