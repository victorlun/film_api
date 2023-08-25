package com.film_api.mapper;

import com.film_api.model.dto.MovieDTO;
import com.film_api.model.Movie;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDTO movieToMovieDTO(Movie movie);

    Movie movieDTOToMovie(MovieDTO movieDTO);
}
