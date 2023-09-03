package com.film_api.repositories;

import com.film_api.models.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitle(String title);
    List<Movie> findByFranchiseId(Long franchiseId);
}
