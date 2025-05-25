package edu.ubb.pricecomparator.infrastructure.repository;

import edu.ubb.pricecomparator.domain.model.PriceEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceEntryJpaRepository extends GenericJpaRepository<PriceEntry, Long> {
    Optional<PriceEntry> findByStoreIdAndProductId(Long storeId, Long productId);
    List<PriceEntry> findByProductId(Long productId);
}
