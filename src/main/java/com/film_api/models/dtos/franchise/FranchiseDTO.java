package com.film_api.models.dtos.franchise;

import com.film_api.models.entities.Franchise;
import com.film_api.models.entities.Movie;
import com.film_api.models.dtos.movie.MovieDTO;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class FranchiseDTO {
    @Schema(description = "Name of the franchise.", example = "Marvel")
    private String name;
    @Schema(description = "Description of the franchise.", example = "A superhero franchise")
    private String description;
    @Schema(description = "Movies in the franchise.")
    @ArraySchema(schema = @Schema(implementation = Movie.class))
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
