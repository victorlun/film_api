package com.film_api.models.dtos.moviecharacter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MovieCharacterPostDTO {
    @Schema(description = "Full name of the character.", example = "Tony Stark")
    private String name;

    @Schema(description = "Alias of the character.", example = "Iron Man")
    private String alias;

    @Schema(description = "Gender of the character.", example = "Male")
    private String gender;

    @Schema(description = "URL of the character's photo.", example = "https://example.com/photo.jpg")
    private String photo;
}