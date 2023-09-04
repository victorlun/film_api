package com.film_api.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(Long id){
        super("Movie with ID " + id + " does not exist");
    }
    public MovieNotFoundException(int id){
        super("Movie with ID " + id + " does not exist");
    }

}
