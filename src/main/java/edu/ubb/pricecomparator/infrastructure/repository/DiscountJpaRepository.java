package edu.ubb.pricecomparator.infrastructure.repository;

import edu.ubb.pricecomparator.domain.model.Discount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiscountJpaRepository extends GenericJpaRepository<Discount, Long> {
    @Query("""
    SELECT d FROM Discount d
    WHERE d.product.id = :productId
      AND d.store.id = :storeId
      AND d.fromDate <= :toDate
      AND d.toDate >= :fromDate
    ORDER BY d.fromDate
""")
    List<Discount> findValidDiscountsBetweenDates(
            @Param("productId") Long productId,
            @Param("storeId") Long storeId,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );

    @Query("""
    SELECT d
    FROM Discount d
    WHERE d.product.id = :productId
      AND d.store.id = :storeId
      AND d.fromDate <= :date
      AND d.toDate > :date
    ORDER BY d.percentageOfDiscount DESC
    LIMIT 1
""")
    Discount findValidDiscountsByDate(
            Long productId,
            Long storeId,
            LocalDate date
    );

    @Query("""
        SELECT d FROM Discount d
        WHERE d.createdAt >= :date
    """)
    List<Discount> findNewDiscountsSince(@Param("date") LocalDate date);

    @Query("""
    SELECT d FROM Discount d
    WHERE d.product.id = :productId
      AND d.fromDate <= :date
      AND d.toDate > :date
    ORDER BY d.percentageOfDiscount DESC
""")
    Discount findMaximumActiveDiscountByProductId(@Param("productId") Long productId, @Param("date") LocalDate date);
}