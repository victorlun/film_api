package com.film_api.mappers;

import com.film_api.models.dtos.movie.MovieDTO;
import com.film_api.models.entities.Movie;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDTO movieToMovieDTO(Movie movie);

    Movie movieDTOToMovie(MovieDTO movieDTO);
}
