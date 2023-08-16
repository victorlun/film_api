package com.film_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/franchises")
public class FranchiseController {

    @Operation(summary = "Get all franchises")
    @GetMapping
    public ResponseEntity<String> getAll(){
        return ResponseEntity.ok("Testing api to get all franchises");
    }

    @Operation(summary = "Get specific franchise")
    @GetMapping("{id}")
    public ResponseEntity<String> getById(@PathVariable int id){
        return ResponseEntity.ok("Testing api to get a specific franchise by id: " + id);
    }

    @Operation(summary = "Post a franchise")
    @PostMapping
    public ResponseEntity<String> postFranchise(){
        return ResponseEntity.ok("Testing api to post a franchise");
    }

    @Operation(summary = "Delete a franchise")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteFranchise(@PathVariable int id){
        return ResponseEntity.ok("Testing api to delete franchise of id: " + id);
    }
}
