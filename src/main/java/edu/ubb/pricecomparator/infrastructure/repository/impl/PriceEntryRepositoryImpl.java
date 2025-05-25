package edu.ubb.pricecomparator.infrastructure.repository.impl;

import edu.ubb.pricecomparator.domain.model.PriceEntry;
import edu.ubb.pricecomparator.domain.repository.PriceEntryRepository;
import edu.ubb.pricecomparator.infrastructure.repository.PriceEntryJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PriceEntryRepositoryImpl implements PriceEntryRepository {
    private final PriceEntryJpaRepository jpa;

    @Override
    public Optional<PriceEntry> findById(Long id) {
        return jpa.findById(id);
    }

    @Override
    public PriceEntry save(PriceEntry entity) {
        return jpa.save(entity);
    }

    @Override
    public void delete(PriceEntry entity) {
        jpa.delete(entity);
    }

    @Override
    public void saveAll(List<PriceEntry> entities) {
        jpa.saveAll(entities);
    }

    @Override
    public Optional<PriceEntry> findByStoreIdAndProductId(Long storeId, Long productId) {
        return jpa.findByStoreIdAndProductId(storeId, productId);
    }

    @Override
    public List<PriceEntry> findByProductId(Long id) {
        return jpa.findByProductId(id);
    }
}
