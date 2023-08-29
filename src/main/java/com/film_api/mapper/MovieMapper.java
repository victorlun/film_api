package com.film_api.mapper;

import com.film_api.model.movie.MovieDTO;
import com.film_api.model.movie.Movie;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDTO movieToMovieDTO(Movie movie);

    Movie movieDTOToMovie(MovieDTO movieDTO);
}
