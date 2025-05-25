package edu.ubb.pricecomparator.infrastructure.repository;

import edu.ubb.pricecomparator.domain.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends GenericJpaRepository<Product, Long> {
}