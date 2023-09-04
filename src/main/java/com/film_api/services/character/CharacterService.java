package com.film_api.services.character;

import com.film_api.models.entities.MovieCharacter;
import com.film_api.services.CrudService;

import java.util.List;

public interface CharacterService extends CrudService<MovieCharacter, Long> {
    List<MovieCharacter> getAllCharactersByFranchiseId(Long franchiseId);
}
