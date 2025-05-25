package edu.ubb.pricecomparator.infrastructure.repository;

import edu.ubb.pricecomparator.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends GenericJpaRepository<User, Long> {
}
