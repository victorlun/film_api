package com.film_api.services.movie;

import com.film_api.models.dtos.movie.MovieDTO;
import com.film_api.models.entities.Movie;
import com.film_api.services.CrudService;

import java.util.List;

public interface MovieService extends CrudService<Movie, Long> {
    void updateCharacters(Long movieId, int[] characterIds);
    List<MovieDTO> getAllMoviesByFranchise(Long franchiseId);
}
