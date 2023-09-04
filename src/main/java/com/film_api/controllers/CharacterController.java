package com.film_api.controllers;

import com.film_api.mappers.MovieCharacterMapper;
import com.film_api.models.dtos.moviecharacter.MovieCharacterDTO;
import com.film_api.models.dtos.moviecharacter.MovieCharacterPostDTO;
import com.film_api.models.entities.MovieCharacter;
import com.film_api.services.character.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/characters")
public class CharacterController {

    private final CharacterService characterService;
    private final MovieCharacterMapper movieCharacterMapper;
    @Autowired
    public CharacterController(CharacterService characterService, MovieCharacterMapper movieCharacterMapper) {
        this.characterService = characterService;

        this.movieCharacterMapper = movieCharacterMapper;
    }
    @Operation(summary = "Get all characters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of characters"),
            @ApiResponse(responseCode = "400", description = "Bad Request, possibly due to invalid query parameters"),
            @ApiResponse(responseCode = "404", description = "Characters not found"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    @GetMapping
    public ResponseEntity<List<MovieCharacterDTO>> getAll() {
        List<MovieCharacter> characters = characterService.findAll().stream().toList();
        List<MovieCharacterDTO> dtoCharacters = new ArrayList<>();
        for(MovieCharacter character : characters){
            dtoCharacters.add(movieCharacterMapper.characterToCharacterDTO(character));
        }
        return new ResponseEntity<>(dtoCharacters, HttpStatus.OK);
    }

    @Operation(summary = "Get a specific character by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the character"),
            @ApiResponse(responseCode = "400", description = "Bad Request, possibly due to invalid ID format"),
            @ApiResponse(responseCode = "404", description = "Character not found"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    @GetMapping("{id}")
    public ResponseEntity<MovieCharacterDTO> getSpecificCharacter(@PathVariable Long id) {
        MovieCharacter movieCharacter = characterService.findById(id);
        MovieCharacterDTO movieCharacterDTO = movieCharacterMapper.characterToCharacterDTO(movieCharacter);
        return new ResponseEntity<>(movieCharacterDTO, HttpStatus.OK);
    }
    @Operation(summary = "Create a new character")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Character successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad Request, possibly due to invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, an unexpected error occurred")
    })
    @PostMapping
    public ResponseEntity<MovieCharacter> createCharacter(@RequestBody MovieCharacterPostDTO dto) {
        try {
            MovieCharacter movieCharacter = movieCharacterMapper.movieCharacterPostDtoToCharacter(dto);
            characterService.add(movieCharacter);

            return new ResponseEntity<>(movieCharacter, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update a character")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Character successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request, possibly due to invalid input"),
            @ApiResponse(responseCode = "404", description = "Not Found, character with the given ID not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, an unexpected error occurred")
    })
    @PutMapping("{id}")
    public ResponseEntity<Void> updateCharacter(@PathVariable Long id, @RequestBody MovieCharacterPostDTO dto) {
        try {
            MovieCharacter existingCharacter = characterService.findById(id);

            if (existingCharacter == null) {
                return ResponseEntity.notFound().build();
            }

            existingCharacter.setName(dto.getName());
            existingCharacter.setAlias(dto.getAlias());
            existingCharacter.setGender(dto.getGender());
            existingCharacter.setPhoto(dto.getPhoto());

            characterService.update(existingCharacter);

            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @Operation(summary = "Delete a character")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Character successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Bad Request, possibly due to invalid input"),
            @ApiResponse(responseCode = "404", description = "Not Found, character with the given ID not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, an unexpected error occurred")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCharacter(@PathVariable Long id) {
        MovieCharacter character = characterService.findById(id);
        String name = character.getName();
        try {
            characterService.deleteById(id);
            return new ResponseEntity<>(name + " deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
