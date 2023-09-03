package com.film_api.controllers;

import com.film_api.mappers.MovieCharacterMapper;
import com.film_api.models.dtos.moviecharacter.MovieCharacterDTO;
import com.film_api.models.dtos.moviecharacter.MovieCharacterPostDTO;
import com.film_api.models.entities.MovieCharacter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.film_api.services.character.CharacterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/characters")
public class CharacterController {

    private final CharacterServiceImpl characterServiceImpl;
    private final MovieCharacterMapper movieCharacterMapper;
    @Autowired
    public CharacterController(CharacterServiceImpl characterServiceImpl, MovieCharacterMapper movieCharacterMapper) {
        this.characterServiceImpl = characterServiceImpl;

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
        List<MovieCharacter> characters = characterServiceImpl.findAll();
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
        MovieCharacter movieCharacter = characterServiceImpl.findById(id);
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
            characterServiceImpl.add(movieCharacter);

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
            MovieCharacter existingCharacter = characterServiceImpl.findById(id);

            if (existingCharacter == null) {
                return ResponseEntity.notFound().build();
            }
            MovieCharacter updatedCharacter = movieCharacterMapper.movieCharacterPostDtoToCharacter(dto);
            existingCharacter.setName(updatedCharacter.getName());
            existingCharacter.setAlias(updatedCharacter.getAlias());
            existingCharacter.setGender(updatedCharacter.getGender());
            existingCharacter.setPhoto(updatedCharacter.getPhoto());

            characterServiceImpl.update(existingCharacter);

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
        MovieCharacter character = characterServiceImpl.findById(id);
        String name = character.getName();
        try {
            characterServiceImpl.deleteById(id);
            return new ResponseEntity<>(name + " deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
