package ru.kulsha.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByProductNameLike(String productNameFilter);

    @Query(value = """
            select * from  products p 
            where (:productNameFilter is null or p.productName like :productNameFilter)
            and (:emailFilter is null or p.email like :emailFilter)
            """, nativeQuery = true)
    List<Product> productsByFilter(String productNameFilter, String emailFilter);

}


//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public List<Product> findAll(){
//        return entityManager.createQuery("select p from Product p", Product.class).getResultList();
//    }
//
//    public Optional<Product> productById(long id){
//        return Optional.ofNullable(entityManager.find(Product.class, id));
//    }
//
//    @Transactional
//    public Product save(Product product){
//        if(product.getId() != null){
//            entityManager.merge(product);
//        } else {
//            entityManager.persist(product);
//        }
//        return product;
//    }
//
//    @Transactional
//    public void deleteById(long id){
//        entityManager.createQuery("delete from Product p where p.id = :id")
//                .setParameter("id", id).executeUpdate();
//    }


//}
