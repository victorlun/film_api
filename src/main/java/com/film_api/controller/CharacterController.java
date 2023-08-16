package com.film_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/characters")
public class CharacterController {
    @Operation(summary = "Get all characters")
    @GetMapping
    public ResponseEntity<String> getAll(){
        return ResponseEntity.ok("Testing api to get all characters");
    }

    @Operation(summary = "Get character by ID")
    @GetMapping("{id}")
    public ResponseEntity<String> getCharacterById(@PathVariable int id){
        return ResponseEntity.ok("Testing api to get a specific character with id "+id);
    }

    @Operation(summary = "Post a character")
    @PostMapping
    public ResponseEntity<String> postCharacter(){
        return ResponseEntity.ok("Testing api to post a character");
    }
    @Operation(summary = "Delete a character")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCharacter(@PathVariable int id){
        return ResponseEntity.ok("Testing api to delete character with id "+ id);
    }
}
