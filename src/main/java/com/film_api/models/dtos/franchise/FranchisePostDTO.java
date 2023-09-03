package com.film_api.models.dtos.franchise;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class FranchisePostDTO {
    private String name;
    private String description;

}
