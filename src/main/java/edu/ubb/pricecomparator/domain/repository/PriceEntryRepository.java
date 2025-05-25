package edu.ubb.pricecomparator.domain.repository;

import edu.ubb.pricecomparator.domain.model.PriceEntry;

import java.util.List;
import java.util.Optional;

public interface PriceEntryRepository extends Repository<PriceEntry, Long> {
    Optional<PriceEntry> findByStoreIdAndProductId(Long storeId, Long productId);
    List<PriceEntry> findByProductId(Long id);
}
