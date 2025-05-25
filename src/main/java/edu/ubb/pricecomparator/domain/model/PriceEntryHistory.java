package edu.ubb.pricecomparator.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "price_entry_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceEntryHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount appliedDiscount;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private LocalDate from_date;

    @Column(nullable = false)
    private LocalDate to_date;

    @Column(name = "created_at")
    private LocalDate createdAt;
}
