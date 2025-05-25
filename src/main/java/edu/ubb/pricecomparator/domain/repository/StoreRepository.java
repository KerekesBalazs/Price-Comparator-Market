package edu.ubb.pricecomparator.domain.repository;

import edu.ubb.pricecomparator.domain.model.Store;

import java.util.Optional;

public interface StoreRepository extends Repository<Store, Long> {
    Optional<Store> findByName(String name);
}
