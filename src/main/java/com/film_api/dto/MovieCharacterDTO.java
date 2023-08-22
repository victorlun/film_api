package com.film_api.dto;

import com.film_api.model.Movie;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MovieCharacterDTO {
    private String name;
    private String alias;
    private Set<Movie> playedInMovies;
}
