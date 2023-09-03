package com.film_api.models.dtos.franchise;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FranchisePostDTO {
    @Schema(description = "Name of the franchise.", example = "Marvel")
    private String name;

    @Schema(description = "Description of the franchise.", example = "A superhero franchise")
    private String description;

}
