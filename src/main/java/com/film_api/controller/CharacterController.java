package com.film_api.controller;

import com.film_api.model.Character;
import com.film_api.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/character")
public class CharacterController {
    @Autowired
    CharacterRepository characterRepository;

    @PostMapping
    public ResponseEntity<Character> createCharacter(@RequestBody Character character) {
        try {
            Character newCharacter = characterRepository
                    .save(new Character(character.getFullName(), character.getAlias(), character.getGender(), character.getPhoto()));
            return new ResponseEntity<>(newCharacter, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
