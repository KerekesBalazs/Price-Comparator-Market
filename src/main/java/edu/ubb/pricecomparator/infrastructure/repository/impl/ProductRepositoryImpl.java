package edu.ubb.pricecomparator.infrastructure.repository.impl;

import edu.ubb.pricecomparator.domain.model.Product;
import edu.ubb.pricecomparator.domain.repository.ProductRepository;
import edu.ubb.pricecomparator.infrastructure.repository.ProductJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJpaRepository jpa;

    @Override
    public Optional<Product> findById(Long id) {
        return jpa.findById(id);
    }

    @Override
    public Product save(Product entity) {
        return jpa.save(entity);
    }

    @Override
    public void delete(Product entity) {
        jpa.delete(entity);
    }

    @Override
    public void saveAll(List<Product> entities) {
        jpa.saveAll(entities);
    }

    @Override
    public List<Product> findAll() {
        return jpa.findAll();
    }
}