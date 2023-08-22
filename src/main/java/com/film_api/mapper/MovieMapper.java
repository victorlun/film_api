package com.film_api.mapper;

import com.film_api.dto.MovieDTO;
import com.film_api.model.Movie;
import org.mapstruct.Mapper;

import java.util.Optional;


@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDTO movieToMovieDTO(Movie movie);
    Movie movieDTOToMovie(MovieDTO movieDTO);
}
