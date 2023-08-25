package com.film_api.service;

import com.film_api.model.MovieCharacter;
import com.film_api.model.dto.MovieDTO;
import com.film_api.mapper.MovieMapper;
import com.film_api.model.Movie;
import com.film_api.repository.MovieCharacterRepository;
import com.film_api.repository.MovieRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieCharacterRepository movieCharacterRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, MovieCharacterRepository movieCharacterRepository) {
        this.movieRepository = movieRepository;
        this.movieCharacterRepository = movieCharacterRepository;
    }

    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private EntityManager entityManager;

    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(movieMapper::movieToMovieDTO)
                .collect(Collectors.toList());
    }

    public Optional<MovieDTO> getMovieById(Long id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        return movieOptional.map(movie -> movieMapper.movieToMovieDTO(movie));
    }

    public void createMovie(MovieDTO movieDto) {
        Movie movie = movieDto.convertDTOToMovie();
        movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie movieDetails) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            movie.setTitle(movieDetails.getTitle());
            movie.setDirector(movieDetails.getDirector());
            movie.setGenre(movieDetails.getGenre());
            movie.setPicture(movie.getPicture());
            movie.setReleaseYear(movieDetails.getReleaseYear());
            movie.setTrailer(movieDetails.getTrailer());
            return movieRepository.save(movie);
        } else {
            throw new RuntimeException("Movie not found with id: " + id);
        }
    }

    @Transactional
    public void deleteMovie(Long id) {

        Query deleteAssociationsQuery = entityManager.createNativeQuery("DELETE FROM characters_movies WHERE movie_id = ?")
                .setParameter(1, id);
        deleteAssociationsQuery.executeUpdate();

        Query deleteCharacterQuery = entityManager.createNativeQuery("DELETE FROM movie WHERE id = ?")
                .setParameter(1, id);

        deleteCharacterQuery.executeUpdate();
    }


    public List<Movie> getMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }


    public void updateCharacters(Long movieId, int[] characterIds) {
        Movie movie = null;
        try {
            movie = movieRepository.findById(movieId).orElseThrow(() -> new Exception("Movie not found"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Set<MovieCharacter> characters = new HashSet<>();
        for (int id : characterIds) {
            MovieCharacter character = null;
            try {
                character = movieCharacterRepository.findById((long) id)
                        .orElseThrow(() -> new Exception("   f"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            characters.add(character);
        }

        movie.setCharacters(characters);
        movieRepository.save(movie);
    }


}
