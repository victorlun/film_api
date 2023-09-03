package com.film_api.models.dtos.movie;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class MovieDTO {
    private String title;
    private String genre;
    private String releaseYear;

}
