package com.film_api.model.dto;

import com.film_api.model.Franchise;
import com.film_api.model.Movie;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class FranchiseDTO {
    private String name;
    private String description;
    private Set<MovieDTO> movies;

    public FranchiseDTO convertToDTO(Franchise franchise) {
        FranchiseDTO dto = new FranchiseDTO();
        dto.setName(franchise.getName());
        dto.setDescription(franchise.getDescription());
        Set<MovieDTO> movieDTOs = franchise.getMovies().stream()
                .map(this::convertMovieToDTO)
                .collect(Collectors.toSet());
        dto.setMovies(movieDTOs);
        return dto;
    }

    public Franchise convertDTOToFranchise() {
        Franchise franchise = new Franchise();
        franchise.setName(this.getName());
        franchise.setDescription(this.getDescription());
        Set<MovieDTO> movieDto = this.getMovies();
        if (movies != null) {
            Set<Movie> movies = movieDto.stream()
                    .map(this::convertMovieDTOToMovie)
                    .collect(Collectors.toSet());
            franchise.setMovies(movies);
        }
        return franchise;
    }

    public MovieDTO convertMovieToDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        //Populate MovieDTO
        dto.setTitle(movie.getTitle());
        dto.setGenre(movie.getGenre());
        dto.setReleaseYear(movie.getReleaseYear());
        return dto;
    }

    public Movie convertMovieDTOToMovie(MovieDTO movieDto) {
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setGenre(movieDto.getGenre());
        movie.setReleaseYear(movieDto.getReleaseYear());
        return movie;
    }

}
