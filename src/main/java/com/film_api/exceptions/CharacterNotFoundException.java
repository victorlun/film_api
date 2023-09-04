package com.film_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CharacterNotFoundException  extends RuntimeException{
    public CharacterNotFoundException(Long id){
        super("MovieCharacter with ID " + id + " does not exist");
    }
    public CharacterNotFoundException(int id){
        super("MovieCharacter with ID " + id + " does not exist");
    }
}
