package edu.ubb.pricecomparator.infrastructure.repository.impl;

import edu.ubb.pricecomparator.domain.model.Discount;
import edu.ubb.pricecomparator.domain.repository.DiscountRepository;
import edu.ubb.pricecomparator.infrastructure.repository.DiscountJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class DiscountRepositoryImpl implements DiscountRepository {
    private final DiscountJpaRepository jpa;

    @Override
    public void saveAll(List<Discount> entities) {
        jpa.saveAll(entities);
    }

    @Override
    public Optional<Discount> findById(Long id) {
        return jpa.findById(id);
    }

    @Override
    public Discount save(Discount entity) {
        return jpa.save(entity);
    }

    @Override
    public void delete(Discount entity) {
        jpa.delete(entity);
    }

    @Override
    public List<Discount> findValidDiscountsBetweenDates(
            Long productId,
            Long storeId,
            LocalDate fromDate,
            LocalDate toDate
    ) {
        return jpa.findValidDiscountsBetweenDates(productId, storeId, fromDate, toDate);
    }

    @Override
    public Discount findValidDiscountsByDate(Long productId, Long storeId, LocalDate date) {
        return jpa.findValidDiscountsByDate(productId, storeId, date);
    }

    @Override
    public List<Discount> findNewDiscountsSince(LocalDate date) {
        return jpa.findNewDiscountsSince(date);
    }

    @Override
    public Discount findMaximumActiveDiscountByProductId(Long productId, LocalDate date) {
        return jpa.findMaximumActiveDiscountByProductId(productId, date);
    }
}
