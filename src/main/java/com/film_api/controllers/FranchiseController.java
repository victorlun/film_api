package com.film_api.controllers;

import com.film_api.models.dtos.franchise.FranchiseDTO;
import com.film_api.models.dtos.franchise.FranchisePostDTO;
import com.film_api.models.dtos.moviecharacter.MovieCharacterDTO;
import com.film_api.models.dtos.movie.MovieDTO;
import com.film_api.services.CharacterService;
import com.film_api.services.FranchiseService;
import com.film_api.services.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/franchises")
public class FranchiseController {

    private final FranchiseService franchiseService;
    private final MovieService movieService;
    private final CharacterService characterService;

    @Autowired
    public FranchiseController(FranchiseService franchiseService, MovieService movieService, CharacterService characterService) {
        this.franchiseService = franchiseService;
        this.movieService = movieService;
        this.characterService = characterService;
    }

    @Operation(summary = "Get all franchises")
    @GetMapping
    public ResponseEntity<List<FranchiseDTO>> getAll() {
        List<FranchiseDTO> franchises = franchiseService.getAllFranchises();
        return ResponseEntity.ok(franchises);
    }

    @Operation(summary = "Get specific franchise")
    @GetMapping("{id}")
    public ResponseEntity<FranchiseDTO> getById(@PathVariable Long id) {
        return franchiseService.getSpecificFranchise(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation (summary = "Get all movies in a franchise")
    @GetMapping("{id}/movies")
    public ResponseEntity<List<MovieDTO>> getAllMovies(@PathVariable Long id){
        List<MovieDTO> movies = movieService.getAllMoviesByFranchise(id);
        return new ResponseEntity<>(movies, HttpStatus.OK);
        }




    @Operation(summary = "Post a franchise")
    @PostMapping
    public ResponseEntity<FranchisePostDTO> postFranchise(@RequestBody FranchisePostDTO franchise) {
        FranchisePostDTO savedFranchise = franchiseService.createFranchise(franchise);
        return ResponseEntity.ok(savedFranchise);
    }


    @Operation(summary = "Update a franchise")
    @PutMapping("{id}")
    public ResponseEntity<FranchisePostDTO> updateFranchise(@PathVariable Long id, @RequestBody FranchisePostDTO franchiseDetails) {
        try {
            FranchisePostDTO updatedFranchise = franchiseService.updateFranchise(id, franchiseDetails);
            return ResponseEntity.ok(updatedFranchise);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Update the list of movies that appear in a given franchise.")
    @PutMapping("{id}/movies")
    public ResponseEntity<Void> updateMoviesInFranchise(@PathVariable Long id, @RequestBody int[] moviesId) {
        franchiseService.updateFranchiseRelation(id, moviesId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a franchise")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteFranchise(@PathVariable Long id) {
        try {
            franchiseService.deleteFranchise(id);
            return ResponseEntity.ok("Deleted franchise with ID: " + id);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Get all characters in a franchise")
    @GetMapping("/{id}/characters")
    public ResponseEntity<List<MovieCharacterDTO>> getCharactersByFranchise(@PathVariable Long id) {
        List<MovieCharacterDTO> characters = characterService.getAllCharactersByFranchiseId(id);
        return new ResponseEntity<>(characters, HttpStatus.OK);
    }
}
