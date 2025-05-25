package edu.ubb.pricecomparator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @Column(name = "id")
    private long id;

    @Column(nullable = false)
    private String name;
    @Column
    private String category;
    @Column
    private String brand;
    @Column
    private Double packageQuantity;
    @Column
    private String packageUnit;

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<PriceAlert> priceAlerts;
    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<PriceEntry> priceEntries;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Discount> discounts;
}
