package ru.kulsha.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "products")
@NamedQueries({
        @NamedQuery(name = "findAllProducts", query = "select p from Product p"),
        @NamedQuery(name = "countAllProducts", query = "select count(p) from Product p"),
        @NamedQuery(name = "deleteProductById", query = "delete from Product p where p.id= :id")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String productName;

    @Column
    private BigDecimal cost;

    @OneToMany(mappedBy = "product",
    cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE},
    orphanRemoval = true)
    private List<Contact> contacts;

    @Column(nullable = false)
    private String password;


    @OneToMany(mappedBy = "product")
    private List<LineItem> lineItems;


    public Product(String productName, BigDecimal cost, List<Contact> contacts, String password) {
        this.productName = productName;
        this.cost = cost;
        this.contacts = contacts;
        this.password = password;
    }
}
