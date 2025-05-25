package edu.ubb.pricecomparator.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "stores")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "store", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<PriceEntry> priceEntries;
    @OneToMany(mappedBy = "store", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Discount> discounts;
}
