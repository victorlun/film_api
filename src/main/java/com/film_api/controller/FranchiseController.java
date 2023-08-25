package com.film_api.controller;

import com.film_api.model.dto.FranchiseDTO;
import com.film_api.model.Franchise;
import com.film_api.service.FranchiseService;
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

    @Autowired
    public FranchiseController(FranchiseService franchiseService) {
        this.franchiseService = franchiseService;
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

    @Operation(summary = "Post a franchise")
    @PostMapping
    public ResponseEntity<Franchise> postFranchise(@RequestBody Franchise franchise) {
        Franchise savedFranchise = franchiseService.createFranchise(franchise);
        return ResponseEntity.ok(savedFranchise);
    }


    @Operation(summary = "Update a franchise")
    @PutMapping("{id}")
    public ResponseEntity<Franchise> updateFranchise(@PathVariable Long id, @RequestBody Franchise franchiseDetails) {
        try {
            Franchise updatedFranchise = franchiseService.updateFranchise(id, franchiseDetails);
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
}
