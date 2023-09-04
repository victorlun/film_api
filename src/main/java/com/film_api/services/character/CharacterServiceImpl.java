package com.film_api.services.character;

import com.film_api.models.dtos.moviecharacter.MovieCharacterDTO;
import com.film_api.mappers.MovieCharacterMapper;
import com.film_api.models.entities.MovieCharacter;
import com.film_api.models.dtos.moviecharacter.MovieCharacterPostDTO;
import com.film_api.repositories.MovieCharacterRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final MovieCharacterRepository movieCharacterRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MovieCharacterMapper movieCharacterMapper;

    @Autowired
    public CharacterServiceImpl(MovieCharacterRepository movieCharacterRepository) {
        this.movieCharacterRepository = movieCharacterRepository;
    }

    @Override
    public List<MovieCharacter> findAll() {
        return movieCharacterRepository.findAll();
    }

    @Override
    public MovieCharacter findById(Long id) {
        return movieCharacterRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
       // return character.map(movieCharacter -> movieCharacterMapper.characterToCharacterDTO(movieCharacter));
    }

    @Override
    public MovieCharacter add(MovieCharacter movieCharacter) {
        return movieCharacterRepository.save(movieCharacter);
    }
    @Override
    public void update(MovieCharacter movieCharacter) {
        movieCharacterRepository.save(movieCharacter);

    }
    @Transactional
    public void deleteById(Long id) {
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

    public List<MovieCharacterDTO> getAllCharactersByMovie(Long movieId) {
        List<MovieCharacter> characters = movieCharacterRepository.findByPlayedInMovies_Id(movieId);

        return characters.stream()
                .map(character -> movieCharacterMapper.characterToCharacterDTO(character))
                .collect(Collectors.toList());
    }
    @Override
    public List<MovieCharacter> getAllCharactersByFranchiseId(Long franchiseId) {

        return movieCharacterRepository.findByPlayedInMovies_Franchise_Id(franchiseId);
    }
}
