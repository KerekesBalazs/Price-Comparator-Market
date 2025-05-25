package edu.ubb.pricecomparator.domain.repository;

import edu.ubb.pricecomparator.domain.model.Product;

import java.util.List;

public interface ProductRepository extends Repository<Product, Long> {
    List<Product> findAll();
}