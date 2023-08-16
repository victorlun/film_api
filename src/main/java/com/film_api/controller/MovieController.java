package com.film_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/movies")
public class MovieController {

    @Operation(summary = "Get all movies")
    @GetMapping
    public ResponseEntity<String> getAll(){
        return ResponseEntity.ok("Testing get all movies api");
    }

    @Operation(summary = "Get specific movie")
    @GetMapping("{id}")
    public ResponseEntity<String> getById(@PathVariable int id){
        return ResponseEntity.ok("Testing to get a specific movie with the id of "+ id);
    }

    @Operation(summary = "Post a movie")
    @PostMapping
    public ResponseEntity<String> postMovie(){
        return ResponseEntity.ok("Testing to Post a movie");
    }

    @Operation(summary = "Delete a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movie successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Malformed request"),
            @ApiResponse(responseCode = "404", description = "No movie found with that id")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete (@PathVariable int id){
        //return ResponseEntity.noContent().build();
            return ResponseEntity.ok("Testing deleting movie api of id "+id);
    }
}
