package com.film_api.repositories;

import com.film_api.models.entities.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FranchiseRepository extends JpaRepository<Franchise, Long> {
    List<Franchise> findByName(String name);
}
