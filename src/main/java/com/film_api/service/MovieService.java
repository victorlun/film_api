package com.film_api.service;

import com.film_api.model.Movie;
import com.film_api.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

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
            movie.setTitle(optionalMovie.get().getTitle());
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
    public void deleteMovie(Long id){
        if(movieRepository.existsById(id)){
            movieRepository.deleteById(id);
        }else {
            throw new RuntimeException("Movie not found with id: " + id);
        }
    }
    public List<Movie> getMovieByTitle(String title){
        return movieRepository.findByTitle(title);
    }






}
