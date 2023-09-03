package com.film_api.models.dtos.franchise;

import com.film_api.models.entities.Franchise;
import com.film_api.models.entities.Movie;
import com.film_api.models.dtos.movie.MovieDTO;
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

    public MovieDTO convertMovieToDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        //Populate MovieDTO
        dto.setTitle(movie.getTitle());
        dto.setGenre(movie.getGenre());
        dto.setReleaseYear(movie.getReleaseYear());
        return dto;
    }

}
