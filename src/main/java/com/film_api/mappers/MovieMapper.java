package com.film_api.mappers;

import com.film_api.models.dtos.movie.MovieDTO;
import com.film_api.models.dtos.movie.MoviePostDTO;
import com.film_api.models.entities.Movie;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public abstract class MovieMapper {

    public abstract Movie moviePostDTOToMovie(MoviePostDTO moviePostDTO);
    public abstract MovieDTO movieToMovieDTO(Movie movie);

    public abstract Movie movieDTOToMovie(MovieDTO movieDTO);
}
