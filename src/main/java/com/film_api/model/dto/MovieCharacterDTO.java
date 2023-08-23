package com.film_api.model.dto;

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
