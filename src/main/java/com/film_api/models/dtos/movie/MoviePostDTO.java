package com.film_api.models.dtos.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
public class MoviePostDTO {
    @Schema(description = "Title of the movie.", example = "Spider-man: Homecoming")
    private String title;
    @Schema(description = "Genre of the movie.", example = "Sci-fi")
    private String genre;
    @Schema(description = "Release year of the movie.", example = "2012")
    private String releaseYear;
    @Schema(description = "Director of the movie.", example = "Lana Wachowski")
    private String director;
    @Schema(description = "URL to the movie's picture.", example = "https://example.com/movie.jpg")
    private String picture;
    @Schema(description = "URL to the movie's trailer.", example = "https://example.com/trailer.mp4")
    private String trailer;
}
