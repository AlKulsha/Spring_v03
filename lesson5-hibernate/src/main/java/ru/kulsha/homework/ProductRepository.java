package ru.kulsha.homework;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import ru.kulsha.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class ProductRepository {

    private final EntityManagerFactory entityManagerFactory;

    public ProductRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Optional<Product> findById(long id) {
        return executeForEntityManager(entityManager ->
                Optional.ofNullable(entityManager.find(Product.class, id)));
    }

    public List<Product> findAll() {
        return executeForEntityManager(entityManager ->
                entityManager.createNamedQuery("findAllProducts", Product.class).getResultList());
    }

    public long count() {
        return executeForEntityManager(entityManager ->
                entityManager.createNamedQuery("countAllProducts", Long.class).getSingleResult());
    }


    public void save(Product product) {
        executeInTransaction(entityManager -> {
            if (product.getId() == null) {
                entityManager.persist(product);   //save if not exists
            } else {
                entityManager.merge(product);     //update if exists
            }
        });
    }

    public void deleteById(Long id) {
        executeInTransaction(entityManager -> {
            entityManager.createNamedQuery("deleteProductById")
                    .setParameter("id", id)
                    .executeUpdate();
        });
    }

    private <R> R executeForEntityManager(Function<EntityManager, R> function) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return function.apply(entityManager);
        } finally {
            entityManager.close();
        }
    }

    private void executeInTransaction(Consumer<EntityManager> consumer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            consumer.accept(entityManager);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }
}