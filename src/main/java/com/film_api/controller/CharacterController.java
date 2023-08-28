package com.film_api.controller;

import com.film_api.model.dto.MovieCharacterDTO;
import com.film_api.model.MovieCharacter;
import com.film_api.model.dto.MovieCharacterPostDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.film_api.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/api/v1/characters")
public class CharacterController {

    private final CharacterService characterService;


    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;

    }

    @Operation(summary = "Get all characters")
    @GetMapping
    public ResponseEntity<List<MovieCharacterDTO>> getAll() {
        List<MovieCharacterDTO> characters = characterService.getAllCharacters();
        return new ResponseEntity<>(characters, HttpStatus.OK);
    }

    @Operation(summary = "Get a specific character by ID")
    @GetMapping("{id}")
    public ResponseEntity<MovieCharacterDTO> getSpecificCharacter(@PathVariable Long id) {
        return characterService.getCharacterById(id)
                .map(character -> new ResponseEntity<>(character, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Create a new character")
    @PostMapping
    public ResponseEntity<MovieCharacterPostDTO> createCharacter(@RequestBody MovieCharacterPostDTO dto) {
        try {
            MovieCharacterPostDTO newMovieCharacterDTO = characterService.createCharacter(dto);
            return new ResponseEntity<>(newMovieCharacterDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update a character")
    @PutMapping("{id}")
    public ResponseEntity<MovieCharacterPostDTO> updateCharacter(@PathVariable Long id, @RequestBody MovieCharacterPostDTO dto) {
        try {
            MovieCharacterPostDTO updatedMovieCharacterDTO = characterService.updateCharacter(id, dto);
            return ResponseEntity.ok(updatedMovieCharacterDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @Operation(summary = "Delete a character")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCharacter(@PathVariable Long id) {
        Optional<MovieCharacterDTO> character = characterService.getCharacterById(id);
        String name = character.get().getName();
        try {
            characterService.deleteCharacter(id);
            return new ResponseEntity<>(name + " deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
