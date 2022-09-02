package ru.kulsha.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "contacts")
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContactType type;

    @Column(nullable = false)
    private String value;

    @ManyToOne
    private Product product;

    public Contact(ContactType type, String value) {
        this.type = type;
        this.value = value;
    }
}
