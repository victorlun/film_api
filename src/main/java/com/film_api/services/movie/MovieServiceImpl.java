package com.film_api.services.movie;

import com.film_api.models.entities.MovieCharacter;
import com.film_api.models.dtos.movie.MovieDTO;
import com.film_api.mappers.MovieMapper;
import com.film_api.models.entities.Movie;
import com.film_api.repositories.MovieCharacterRepository;
import com.film_api.repositories.MovieRepository;
import com.film_api.exceptions.CharacterNotFoundException;
import com.film_api.exceptions.MovieNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieCharacterRepository movieCharacterRepository;
    private final EntityManager entityManager;
    private final MovieMapper movieMapper;
    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, MovieCharacterRepository movieCharacterRepository, EntityManager entityManager, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieCharacterRepository = movieCharacterRepository;
        this.entityManager = entityManager;
        this.movieMapper = movieMapper;
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }
    @Override
    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
    }
    @Override
    public Movie add(Movie movie) {
        return movieRepository.save(movie);
    }
    @Override
    public void update(Movie movie) {
        movieRepository.save(movie);
    }



    @Transactional
    public void deleteById(Long id) {
        //Couldn't get it to work using the JPA-functionality
        Query deleteAssociationsQuery = entityManager.createNativeQuery("DELETE FROM characters_movies WHERE movie_id = ?")
                .setParameter(1, id);
        deleteAssociationsQuery.executeUpdate();

        Query deleteCharacterQuery = entityManager.createNativeQuery("DELETE FROM movie WHERE id = ?")
                .setParameter(1, id);

        deleteCharacterQuery.executeUpdate();
    }

    @Override
    public void updateCharacters(Long movieId, int[] characterIds) {
        Movie movie;
        try {
            movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Set<MovieCharacter> characters = new HashSet<>();
        for (int id : characterIds) {
            MovieCharacter character;
            try {
                character = movieCharacterRepository.findById((long) id)
                        .orElseThrow(() -> new CharacterNotFoundException(id));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            characters.add(character);
        }

        movie.setCharacters(characters);
        movieRepository.save(movie);
    }
    @Override
    public List<MovieDTO> getAllMoviesByFranchise(Long franchiseId) {
        List<Movie> movies = movieRepository.findByFranchiseId(franchiseId);
        // Convert to DTOs
        return movies.stream()
                .map(movieMapper::movieToMovieDTO)
                .collect(Collectors.toList());
    }

}
