package com.coffee.store.repository;


import com.coffee.store.domain.CoffeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoffeeRepository extends JpaRepository<CoffeeEntity, Long> {
    Optional<CoffeeEntity> findByName(String name);
}
