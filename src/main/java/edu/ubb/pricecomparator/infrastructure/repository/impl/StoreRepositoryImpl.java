package edu.ubb.pricecomparator.infrastructure.repository.impl;

import edu.ubb.pricecomparator.domain.model.Store;
import edu.ubb.pricecomparator.domain.repository.StoreRepository;
import edu.ubb.pricecomparator.infrastructure.repository.StoreJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class StoreRepositoryImpl implements StoreRepository {
    private final StoreJpaRepository jpa;

    @Override
    public Optional<Store> findById(Long id) {
        return jpa.findById(id);
    }

    @Override
    public Store save(Store entity) {
        return jpa.save(entity);
    }

    @Override
    public void delete(Store entity) {
        jpa.delete(entity);
    }

    @Override
    public void saveAll(List<Store> entities) {
        jpa.saveAll(entities);
    }

    @Override
    public Optional<Store> findByName(String name) {
        return jpa.findByName(name);
    }
}
