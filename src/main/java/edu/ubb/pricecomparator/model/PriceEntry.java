package edu.ubb.pricecomparator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "price_entities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String currency;

    @Column(name = "created_at")
    private LocalDate createdAt;
}