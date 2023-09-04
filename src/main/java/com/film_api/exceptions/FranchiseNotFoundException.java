package com.film_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FranchiseNotFoundException extends RuntimeException{
 public FranchiseNotFoundException(Long id){
     super("Franchise with ID " + id + " does not exist");
 }
}
