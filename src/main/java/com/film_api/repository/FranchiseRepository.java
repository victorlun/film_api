package com.film_api.repository;

import com.film_api.model.franchise.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FranchiseRepository extends JpaRepository<Franchise, Long> {
    List<Franchise> findByName(String name);
}
