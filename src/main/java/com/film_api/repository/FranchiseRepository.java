package com.film_api.repository;

import com.film_api.model.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FranchiseRepository extends JpaRepository<Franchise, Long> {
    List<Franchise> findByTitle(String title);

}
