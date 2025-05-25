package edu.ubb.pricecomparator.infrastructure.repository.impl;

import edu.ubb.pricecomparator.domain.model.PriceEntryHistory;
import edu.ubb.pricecomparator.domain.repository.PriceEntryHistoryRepository;
import edu.ubb.pricecomparator.infrastructure.repository.PriceEntryHistoryJpaRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Repository
public class PriceEntryHistoryRepositoryImpl implements PriceEntryHistoryRepository {
    private final PriceEntryHistoryJpaRepository jpa;

    @Override
    public Optional<PriceEntryHistory> findById(Long id) {
        return jpa.findById(id);
    }

    @Override
    public PriceEntryHistory save(PriceEntryHistory entity) {
        return jpa.save(entity);
    }

    @Override
    public void delete(PriceEntryHistory entity) {
        jpa.delete(entity);
    }

    @Override
    public void saveAll(List<PriceEntryHistory> entities) {
        jpa.saveAll(entities);
    }
}
