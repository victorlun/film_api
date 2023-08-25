package com.film_api.model.dto;

import com.film_api.model.Movie;
import com.film_api.model.MovieCharacter;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class MovieDTO {
    private String title;
    private String genre;
    private String releaseYear;
    private Set<MovieCharacterDTO> charactersInMovies;

    public Movie convertDTOToMovie() {
        Movie movie = new Movie();
        movie.setTitle(this.getTitle());
        movie.setGenre(this.getGenre());
        movie.setReleaseYear(this.getReleaseYear());
        Set<MovieCharacterDTO> charactersDto = this.getCharactersInMovies();
        if (charactersDto != null) {
            Set<MovieCharacter> characters = charactersDto.stream()
                    .map(this::convertCharacterDTOToCharacter)
                    .collect(Collectors.toSet());
            movie.setCharacters(characters);
        }
        return movie;
    }

    public MovieCharacter convertCharacterDTOToCharacter(MovieCharacterDTO characterDto) {
        MovieCharacter character = new MovieCharacter();
        character.setName(characterDto.getName());
        character.setAlias(character.getAlias());
        character.setGender(characterDto.getGender());
        return character;
    }
}
