package edu.ubb.pricecomparator.infrastructure.repository.impl;

import edu.ubb.pricecomparator.domain.model.User;
import edu.ubb.pricecomparator.domain.repository.UserRepository;
import edu.ubb.pricecomparator.infrastructure.repository.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository jpa;

    @Override
    public Optional<User> findById(Long id) {
        return jpa.findById(id);
    }

    @Override
    public User save(User entity) {
        return jpa.save(entity);
    }

    @Override
    public void delete(User entity) {
        jpa.delete(entity);
    }

    @Override
    public void saveAll(List<User> entities) {
        jpa.saveAll(entities);
    }
}
