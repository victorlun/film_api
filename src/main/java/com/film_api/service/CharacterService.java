package com.film_api.service;

import com.film_api.model.movie_character.MovieCharacterDTO;
import com.film_api.mapper.MovieCharacterMapper;
import com.film_api.model.movie_character.MovieCharacter;
import com.film_api.model.movie_character.MovieCharacterPostDTO;
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


    public MovieCharacterPostDTO createCharacter(MovieCharacterPostDTO dto) {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setName(dto.getName());
        movieCharacter.setAlias(dto.getAlias());
        movieCharacter.setGender(dto.getGender());
        movieCharacter.setPhoto(dto.getPhoto());
        MovieCharacter savedCharacter = movieCharacterRepository.save(movieCharacter);

        // convert saved MovieCharacter to MovieCharacterPostDTO
        MovieCharacterPostDTO savedDto = new MovieCharacterPostDTO();
        savedDto.setName(savedCharacter.getName());
        savedDto.setAlias(savedCharacter.getAlias());
        savedDto.setGender(savedCharacter.getGender());
        savedDto.setPhoto(savedCharacter.getPhoto());

        return savedDto;
    }
    public MovieCharacterPostDTO updateCharacter(Long id, MovieCharacterPostDTO dto) {
        Optional<MovieCharacter> optionalCharacter = movieCharacterRepository.findById(id);

        if (optionalCharacter.isPresent()) {
            MovieCharacter movieCharacter = optionalCharacter.get();
            movieCharacter.setName(dto.getName());
            movieCharacter.setAlias(dto.getAlias());
            movieCharacter.setGender(dto.getGender());
            movieCharacter.setPhoto(dto.getPhoto());

            MovieCharacter updatedCharacter = movieCharacterRepository.save(movieCharacter);

            // convert updated MovieCharacter to MovieCharacterPostDTO
            MovieCharacterPostDTO updatedDto = new MovieCharacterPostDTO();
            updatedDto.setName(updatedCharacter.getName());
            updatedDto.setAlias(updatedCharacter.getAlias());
            updatedDto.setGender(updatedCharacter.getGender());
            updatedDto.setPhoto(updatedCharacter.getPhoto());

            return updatedDto;
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

    public List<MovieCharacterDTO> getAllCharactersByMovie(Long movieId) {
        List<MovieCharacter> characters = movieCharacterRepository.findByPlayedInMovies_Id(movieId);
        // Convert to DTOs
        List<MovieCharacterDTO> characterDTOs = characters.stream()
                .map(character -> movieCharacterMapper.characterToCharacterDTO(character))
                .collect(Collectors.toList());
        return characterDTOs;
    }
    public List<MovieCharacterDTO> getAllCharactersByFranchiseId(Long franchiseId) {
        List<MovieCharacter> movieCharacters = movieCharacterRepository.findByPlayedInMovies_Franchise_Id(franchiseId);
        List<MovieCharacterDTO> characterDTOList = movieCharacters.stream()
                .map(movieCharacter -> movieCharacterMapper.characterToCharacterDTO(movieCharacter))
                .toList();
        return characterDTOList;
    }
}
