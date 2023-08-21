package com.film_api.controller;

import com.film_api.model.Franchise;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.film_api.model.Character;
import org.springframework.http.HttpStatus;
import com.film_api.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


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
    public ResponseEntity<List<Character>> getAll() {
        List<Character> characters = characterService.getAllCharacters();
        return new ResponseEntity<>(characters, HttpStatus.OK);
    }

    @Operation(summary = "Get a specific character by ID")
    @GetMapping("{id}")
    public ResponseEntity<Character> getSpecificCharacter(@PathVariable Long id) {
        return characterService.getCharacterById(id)
                .map(character -> new ResponseEntity<>(character, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Create a new character")
    @PostMapping
    public ResponseEntity<Character> createCharacter(@RequestBody Character character) {
        try {
            Character newCharacter = characterService.createCharacter(character);
            return new ResponseEntity<>(newCharacter, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Update a franchise")
    @PutMapping("{id}")
    public ResponseEntity<Character> updateCharacter(@PathVariable Long id, @RequestBody Character characterDetails) {
        try {
            Character updatedCharacter = characterService.updateCharacter(id, characterDetails);
            return ResponseEntity.ok(updatedCharacter);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a character")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCharacter(@PathVariable Long id) {
        try {
            characterService.deleteCharacter(id);
            return new ResponseEntity<>("Character deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
