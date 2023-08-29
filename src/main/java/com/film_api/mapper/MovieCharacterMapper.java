package com.film_api.mapper;

import com.film_api.model.movie_character.MovieCharacterDTO;
import com.film_api.model.movie.MovieDTO;
import com.film_api.model.movie.Movie;
import com.film_api.model.movie_character.MovieCharacter;
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

