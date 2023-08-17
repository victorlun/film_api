package com.film_api.service;

import com.film_api.model.Character;
import com.film_api.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    @Autowired
    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public List<Character> getAllCharacters() {
        return characterRepository.findAll();
    }

    public Optional<Character> getCharacterById(Long id) {
        return characterRepository.findById(id);
    }

    public Character createCharacter(Character character) {
        return characterRepository.save(character);
    }

    public Character updateCharacter(Long id, Character characterDetails) {
        Optional<Character> optionalCharacter = characterRepository.findById(id);

        if (optionalCharacter.isPresent()) {
            Character character = optionalCharacter.get();
            character.setFullName(characterDetails.getFullName());
            character.setAlias(characterDetails.getAlias());
            character.setGender(characterDetails.getGender());
            character.setPhoto(characterDetails.getPhoto());
            return characterRepository.save(character);
        } else {
            throw new RuntimeException("Character not found with id: " + id);
        }
    }

    public void deleteCharacter(Long id) {
        if (characterRepository.existsById(id)) {
            characterRepository.deleteById(id);
        } else {
            throw new RuntimeException("Character not found with id: " + id);
        }
    }

    public List<Character> getCharactersByName(String name) {
        return characterRepository.findByName(name);
    }
}
