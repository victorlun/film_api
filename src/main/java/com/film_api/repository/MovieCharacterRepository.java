package com.film_api.repository;

import com.film_api.model.movie_character.MovieCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieCharacterRepository extends JpaRepository<MovieCharacter, Long> {
    List<MovieCharacter> findByName(String name);
    List<MovieCharacter> findByPlayedInMovies_Id(Long movieId);
    List<MovieCharacter> findByPlayedInMovies_Franchise_Id(Long franchiseId);
    }


