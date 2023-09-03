package com.film_api.models.dtos.moviecharacter;

import com.film_api.models.dtos.movie.MovieDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MovieCharacterDTO {
    private String name;
    private String alias;
    private Set<MovieDTO> playedInMovies;
}
