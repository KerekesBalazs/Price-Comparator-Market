package edu.ubb.pricecomparator.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface GenericJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    // shared JPA methods can go here
}