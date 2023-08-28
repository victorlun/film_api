package com.film_api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoviePostDTO {
    private String title;

    private String genre;

    private String releaseYear;

    private String director;

    private String picture;

    private String trailer;
}
