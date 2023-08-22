package com.film_api.mapper;

import com.film_api.dto.MovieCharacterDTO;
import com.film_api.dto.MovieDTO;
import com.film_api.model.Movie;
import com.film_api.model.MovieCharacter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring", uses = MovieMapper.class) // add uses = MovieMapper.class to reuse the MovieMapper
public interface MovieCharacterMapper {
    @Mapping(source = "name", target = "name")
    MovieCharacterDTO characterToCharacterDTO(MovieCharacter movieCharacter);
    Character characterDTOtoCharacter(MovieCharacterDTO movieCharacterDTO);

    // Add this method for mapping Set<Movie> to Set<MovieDTO>
    Set<MovieDTO> moviesToMovieDTOs(Set<Movie> movies);
}

