package com.film_api.mappers;

import com.film_api.models.dtos.moviecharacter.MovieCharacterDTO;
import com.film_api.models.dtos.movie.MovieDTO;
import com.film_api.models.dtos.moviecharacter.MovieCharacterPostDTO;
import com.film_api.models.entities.Movie;
import com.film_api.models.entities.MovieCharacter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class MovieCharacterMapper {
    public abstract MovieCharacter movieCharacterPostDtoToCharacter(MovieCharacterPostDTO movieCharacterPostDTO);
    public abstract MovieCharacter characterDTOtoCharacter(MovieCharacterDTO movieCharacterDTO);

    @Mapping(source = "playedInMovies", target = "playedInMovies")
    public abstract MovieCharacterDTO characterToCharacterDTO(MovieCharacter movieCharacter);

    public abstract Set<MovieDTO> moviesToMovieDTOs(Set<Movie> movies);
}
