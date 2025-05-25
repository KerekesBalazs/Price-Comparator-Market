package edu.ubb.pricecomparator.infrastructure.repository;

import edu.ubb.pricecomparator.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreJpaRepository extends GenericJpaRepository<Store, Long> {
    Optional<Store> findByName(String name);
}
