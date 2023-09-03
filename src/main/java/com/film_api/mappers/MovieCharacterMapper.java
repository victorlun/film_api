package com.film_api.mappers;

import com.film_api.models.dtos.moviecharacter.MovieCharacterDTO;
import com.film_api.models.dtos.movie.MovieDTO;
import com.film_api.models.entities.Movie;
import com.film_api.models.entities.MovieCharacter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring", uses = MovieMapper.class)
public interface MovieCharacterMapper {
    @Mapping(source = "playedInMovies", target = "playedInMovies")
    MovieCharacterDTO characterToCharacterDTO(MovieCharacter movieCharacter);

    Character characterDTOtoCharacter(MovieCharacterDTO movieCharacterDTO);

    Set<MovieDTO> moviesToMovieDTOs(Set<Movie> movies);
}

