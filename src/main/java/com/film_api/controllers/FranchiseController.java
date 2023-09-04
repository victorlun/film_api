package com.film_api.controllers;

import com.film_api.mappers.FranchiseMapper;
import com.film_api.mappers.MovieCharacterMapper;
import com.film_api.models.dtos.franchise.FranchiseDTO;
import com.film_api.models.dtos.franchise.FranchisePostDTO;
import com.film_api.models.dtos.moviecharacter.MovieCharacterDTO;
import com.film_api.models.dtos.movie.MovieDTO;
import com.film_api.models.entities.Franchise;
import com.film_api.models.entities.MovieCharacter;
import com.film_api.services.character.CharacterService;
import com.film_api.services.franchise.FranchiseService;
import com.film_api.services.movie.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/franchises")
public class FranchiseController {
    private final FranchiseService franchiseService;
    private final MovieService movieService;
    private final CharacterService characterService;
    private final FranchiseMapper franchiseMapper;
    private final MovieCharacterMapper movieCharacterMapper;

    @Autowired
    public FranchiseController(FranchiseService franchiseService, MovieService movieService, CharacterService characterService, FranchiseMapper franchiseMapper, MovieCharacterMapper movieCharacterMapper) {
        this.franchiseService = franchiseService;
        this.movieService = movieService;
        this.characterService = characterService;
        this.franchiseMapper = franchiseMapper;
        this.movieCharacterMapper = movieCharacterMapper;
    }

    @Operation(summary = "Get all franchises")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of franchises"),
            @ApiResponse(responseCode = "400", description = "Bad Request, possibly due to invalid query parameters"),
            @ApiResponse(responseCode = "404", description = "Franchises not found"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    @GetMapping
    public ResponseEntity<List<FranchiseDTO>> getAll() {
        List<Franchise> franchises = franchiseService.findAll().stream().toList();
        List<FranchiseDTO> dtoFranchises = new ArrayList<>();
        for(Franchise franchise : franchises){
            dtoFranchises.add(franchiseMapper.franchiseToFranchiseDTO(franchise));
        }
        return new ResponseEntity<>(dtoFranchises, HttpStatus.OK);
    }

    @Operation(summary = "Get specific franchise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the franchise"),
            @ApiResponse(responseCode = "400", description = "Bad Request, possibly due to invalid ID format"),
            @ApiResponse(responseCode = "404", description = "Franchise not found"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    @GetMapping("{id}")
    public ResponseEntity<FranchiseDTO> getById(@PathVariable Long id) {
        Franchise franchise = franchiseService.findById(id);
        FranchiseDTO franchiseDTO = franchiseMapper.franchiseToFranchiseDTO(franchise);

        return new ResponseEntity<>(franchiseDTO, HttpStatus.OK);
    }

    @Operation (summary = "Get all movies in a franchise")

    @GetMapping("{id}/movies")
    public ResponseEntity<List<MovieDTO>> getAllMovies(@PathVariable Long id){
        List<MovieDTO> movies = movieService.getAllMoviesByFranchise(id);
        return new ResponseEntity<>(movies, HttpStatus.OK);
        }

    @Operation(summary = "Create a new franchise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Franchise successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad Request, possibly due to invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, an unexpected error occurred")
    })
    @PostMapping
    public ResponseEntity<Franchise> postFranchise(@RequestBody FranchisePostDTO franchisePostDTO) {
        Franchise franchise = franchiseService.add(franchiseMapper.franchisePostDtoToFranchise(franchisePostDTO));

        return new ResponseEntity<>(franchise, HttpStatus.CREATED);
    }


    @Operation(summary = "Update a franchise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Franchise successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request, possibly due to invalid input"),
            @ApiResponse(responseCode = "404", description = "Not Found, franchise with the given ID not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, an unexpected error occurred")
    })
    @PutMapping("{id}")
    public ResponseEntity<Void> updateFranchise(@PathVariable Long id, @RequestBody FranchisePostDTO dto) {
        try {
            Franchise existingFranchise = franchiseService.findById(id);

            if(existingFranchise == null){
                return ResponseEntity.notFound().build();
            }
            existingFranchise.setName(dto.getName());
            existingFranchise.setDescription(dto.getDescription());

            franchiseService.update(existingFranchise);

            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Update the list of movies that appear in a given franchise.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully updated the list of movies in the franchise. No content."),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid id or movie IDs format"),
            @ApiResponse(responseCode = "404", description = "Franchise or one of the movies not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("{id}/movies")
    public ResponseEntity<Void> updateMoviesInFranchise(@PathVariable Long id, @RequestBody int[] moviesId) {
        franchiseService.updateFranchiseRelation(id, moviesId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a franchise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Franchise successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Bad Request, possibly due to invalid input"),
            @ApiResponse(responseCode = "404", description = "Not Found, franchise with the given ID not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, an unexpected error occurred")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteFranchise(@PathVariable Long id) {
        try {
            franchiseService.deleteById(id);
            return ResponseEntity.ok("Deleted franchise with ID: " + id);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Get all characters in a franchise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of characters"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid id format"),
            @ApiResponse(responseCode = "404", description = "Franchise not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}/characters")
    public ResponseEntity<List<MovieCharacterDTO>> getCharactersByFranchise(@PathVariable Long id) {
        List<MovieCharacter> characters = characterService.getAllCharactersByFranchiseId(id);


        List<MovieCharacterDTO> characterDTOList = characters.stream()
                .map(movieCharacterMapper::characterToCharacterDTO)
                .toList();
        return new ResponseEntity<>(characterDTOList, HttpStatus.OK);
    }
}
