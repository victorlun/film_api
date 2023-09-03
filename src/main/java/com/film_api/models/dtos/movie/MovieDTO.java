package com.film_api.models.dtos.movie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDTO {
    private String title;
    private String genre;
    private String releaseYear;

}
