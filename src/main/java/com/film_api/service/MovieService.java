package com.film_api.service;

import com.film_api.model.Movie;
import com.film_api.repository.MovieRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.query.QueryProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @Autowired
    private EntityManager entityManager;

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }
    public Optional<Movie> getMovieById(Long id){
        return movieRepository.findById(id);
    }
    public Movie createMovie(Movie movie){
        return movieRepository.save(movie);
    }
    public Movie updateMovie (Long id, Movie movieDetails){
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()){
            Movie movie = optionalMovie.get();
            movie.setTitle(movieDetails.getTitle());
            movie.setDirector(movieDetails.getDirector());
            movie.setGenre(movieDetails.getGenre());
            movie.setPicture(movie.getPicture());
            movie.setReleaseYear(movieDetails.getReleaseYear());
            movie.setTrailer(movieDetails.getTrailer());
            return movieRepository.save(movie);
        }else{
            throw new RuntimeException("Movie not found with id: "+ id);
        }
    }

    /*  Old deleteMovie function
    public void deleteMovie(Long id){
        if(movieRepository.existsById(id)){
            movieRepository.deleteById(id);
        }else {
            throw new RuntimeException("Movie not found with id: " + id);
        }
    }
    */

    @Transactional
    public void deleteMovie(Long id) {

        Query deleteAssociationsQuery = entityManager.createNativeQuery("DELETE FROM characters_movies WHERE movie_id = ?")
                .setParameter(1, id);
        deleteAssociationsQuery.executeUpdate();

        Query deleteCharacterQuery = entityManager.createNativeQuery("DELETE FROM movie WHERE id = ?")
                .setParameter(1, id);

        deleteCharacterQuery.executeUpdate();
    }


    public List<Movie> getMovieByTitle(String title){
        return movieRepository.findByTitle(title);
    }






}
