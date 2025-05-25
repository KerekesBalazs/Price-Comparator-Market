package edu.ubb.pricecomparator.infrastructure.repository.impl;

import edu.ubb.pricecomparator.domain.model.PriceAlert;
import edu.ubb.pricecomparator.domain.repository.PriceAlertRepository;
import edu.ubb.pricecomparator.infrastructure.repository.PriceAlertJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class PriceAlertRepositoryImpl implements PriceAlertRepository {
    private final PriceAlertJpaRepository jpa;

    @Override
    public Optional<PriceAlert> findById(Long id) {
        return jpa.findById(id);
    }

    @Override
    public PriceAlert save(PriceAlert entity) {
        return jpa.save(entity);
    }

    @Override
    public void delete(PriceAlert entity) {
        jpa.delete(entity);
    }

    @Override
    public void saveAll(List<PriceAlert> entities) {
        jpa.saveAll(entities);
    }
}
