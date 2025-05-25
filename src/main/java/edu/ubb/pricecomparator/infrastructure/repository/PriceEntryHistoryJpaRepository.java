package edu.ubb.pricecomparator.infrastructure.repository;

import edu.ubb.pricecomparator.domain.model.PriceEntryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceEntryHistoryJpaRepository extends GenericJpaRepository<PriceEntryHistory, Long> {
}
