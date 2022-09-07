package ru.kulsha.persist;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @Column(nullable = false, unique = true)
    private String productName;

   @Column(nullable = false)
    private int cost;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 1024)
    private String password;

    @Transient      //т.е. сущность не должна сохраняться в БД
    private String matchingPassword;



    public Product(String productName) {
        this.productName = productName;
    }

}
