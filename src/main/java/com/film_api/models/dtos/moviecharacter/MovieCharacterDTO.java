package com.film_api.models.dtos.moviecharacter;

import com.film_api.models.dtos.movie.MovieDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
public class MovieCharacterDTO {
    @Schema(description = "Full name of the character.", example = "Tony Stark")
    private String name;
    @Schema(description = "Alias of the character.", example = "Iron Man")
    private String alias;
    @Schema(description = "Set of movies the character has played in.")
    private Set<MovieDTO> playedInMovies;
}
