package com.film_api.model.dto;

import com.film_api.model.Franchise;
import com.film_api.model.Movie;
import com.film_api.model.MovieCharacter;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class MovieCharacterDTO {
    private String name;
    private String alias;
    private String gender;
    private Set<MovieDTO> playedInMovies;

    public MovieCharacter convertDTOToCharacter() {
        MovieCharacter character = new MovieCharacter();
        character.setName(this.getName());
        character.setAlias(this.getAlias());
        character.setGender(this.getGender());
        Set<MovieDTO> movieDto = this.getPlayedInMovies();
        if (movieDto != null) {
            Set<Movie> movies = movieDto.stream()
                    .map(this::convertMovieDTOToMovie)
                    .collect(Collectors.toSet());
            character.setPlayedInMovies(movies);
        }
        return character;
    }

    public Movie convertMovieDTOToMovie(MovieDTO movieDto) {
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setGenre(movieDto.getGenre());
        movie.setReleaseYear(movieDto.getReleaseYear());
        return movie;
    }
}
