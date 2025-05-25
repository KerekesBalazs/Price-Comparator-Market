package edu.ubb.pricecomparator.domain.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    Optional<T> findById(ID id);
    T save(T entity);
    void delete(T entity);
    void saveAll(List<T> entities);
}
