package edu.ubb.pricecomparator.domain.repository;

import edu.ubb.pricecomparator.domain.model.Discount;

import java.time.LocalDate;
import java.util.List;

public interface DiscountRepository extends Repository<Discount, Long> {
    List<Discount> findValidDiscountsBetweenDates(
            Long productId,
            Long storeId,
            LocalDate fromDate,
            LocalDate toDate
    );
    Discount findValidDiscountsByDate(
            Long productId,
            Long storeId,
            LocalDate date
    );
    List<Discount> findNewDiscountsSince(LocalDate date);
    Discount findMaximumActiveDiscountByProductId(Long productId, LocalDate date);
}
