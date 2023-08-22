package com.film_api.service;

import com.film_api.model.dto.MovieCharacterDTO;
import com.film_api.mapper.MovieCharacterMapper;
import com.film_api.model.MovieCharacter;
import com.film_api.repository.MovieCharacterRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CharacterService {

    private final MovieCharacterRepository movieCharacterRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MovieCharacterMapper movieCharacterMapper;
    @Autowired
    public CharacterService(MovieCharacterRepository movieCharacterRepository) {
        this.movieCharacterRepository = movieCharacterRepository;
    }

    public List<MovieCharacterDTO> getAllCharacters() {
        List<MovieCharacter> movieCharacters = movieCharacterRepository.findAll();
        return movieCharacters.stream()
                .map(movieCharacterMapper::characterToCharacterDTO)
                .collect(Collectors.toList());
    }

    public Optional<MovieCharacterDTO> getCharacterById(Long id) {
        Optional<MovieCharacter> character = movieCharacterRepository.findById(id);
        return character.map(movieCharacterMapper::characterToCharacterDTO); // Convert to MovieCharacterDTO if present
    }


    public MovieCharacter createCharacter(MovieCharacter movieCharacter) {
        return movieCharacterRepository.save(movieCharacter);
    }

    public MovieCharacter updateCharacter(Long id, MovieCharacter movieCharacterDetails) {
        Optional<MovieCharacter> optionalCharacter = movieCharacterRepository.findById(id);

        if (optionalCharacter.isPresent()) {
            MovieCharacter movieCharacter = optionalCharacter.get();
            movieCharacter.setFullName(movieCharacterDetails.getName());
            movieCharacter.setAlias(movieCharacterDetails.getAlias());
            movieCharacter.setGender(movieCharacterDetails.getGender());
            movieCharacter.setPhoto(movieCharacterDetails.getPhoto());
            return movieCharacterRepository.save(movieCharacter);
        } else {
            throw new RuntimeException("Character not found with id: " + id);
        }
    }

    @Transactional
    public void deleteCharacter(Long id) {
        Query deleteAssociationsQuery = entityManager.createNativeQuery("DELETE FROM characters_movies WHERE character_id = ?")
                .setParameter(1, id);
        deleteAssociationsQuery.executeUpdate();

        Query deleteCharacterQuery = entityManager.createNativeQuery("DELETE FROM character WHERE id = ?")
                .setParameter(1, id);

        deleteCharacterQuery.executeUpdate();
    }

    public List<MovieCharacter> getCharactersByName(String name) {
        return movieCharacterRepository.findByName(name);
    }
}
