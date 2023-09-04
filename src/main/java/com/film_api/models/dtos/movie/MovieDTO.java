package com.film_api.models.dtos.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class MovieDTO {
    @Schema(description = "Title of the movie.", example = "Spider-man: Homecoming")
    private String title;
    @Schema(description = "Genre of the movie.", example = "Sci-fi")
    private String genre;
    @Schema(description = "Release year of the movie.", example = "2012")
    private String releaseYear;

}
