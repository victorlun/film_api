package com.film_api.controllers;

import com.film_api.models.dtos.moviecharacter.MovieCharacterDTO;
import com.film_api.models.dtos.movie.MovieDTO;
import com.film_api.models.entities.Movie;
import com.film_api.models.dtos.movie.MoviePostDTO;
import com.film_api.services.character.CharacterServiceImpl;
import com.film_api.services.movie.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/movies")
public class MovieController {
    private final MovieService movieService;
    private final CharacterServiceImpl characterServiceImpl;
    @Autowired
    public MovieController(MovieService movieService, CharacterServiceImpl characterServiceImpl){
        this.movieService = movieService;
        this.characterServiceImpl = characterServiceImpl;
    }

    @Operation(summary = "Get all movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of movies"),
            @ApiResponse(responseCode = "400", description = "Bad Request, possibly due to invalid query parameters"),
            @ApiResponse(responseCode = "404", description = "Movies not found"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAll() {
        List<MovieDTO> movies = movieService.getAllMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @Operation(summary = "Get specific movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved movie details"),
            @ApiResponse(responseCode = "400", description = "Bad Request, check if the ID is valid"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    @GetMapping("{id}")
    public ResponseEntity<MovieDTO> getSpecificMovie(@PathVariable Long id) {
        return movieService.getMovieById(id)
                .map(movieDTO -> new ResponseEntity<>(movieDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Get all characters in a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of characters"),
            @ApiResponse(responseCode = "400", description = "Bad Request, check if movieId is valid"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    @GetMapping("/movies/{movieId}/characters")
    public ResponseEntity<List<MovieCharacterDTO>> getAllCharactersByMovie(@PathVariable Long movieId) {
        List<MovieCharacterDTO> characters = characterServiceImpl.getAllCharactersByMovie(movieId);
        return new ResponseEntity<>(characters, HttpStatus.OK);
    }



    @Operation(summary = "Post a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    @PostMapping
    public ResponseEntity<Movie> postMovie(@RequestBody MoviePostDTO movieDto) {
        try {
            MoviePostDTO newMovieDto = movieService.createMovie(movieDto);
            Movie newMovie = new Movie();
            newMovie.setTitle(newMovieDto.getTitle());
            newMovie.setGenre(newMovieDto.getGenre());
            newMovie.setReleaseYear(newMovieDto.getReleaseYear());
            newMovie.setDirector(newMovieDto.getDirector());
            newMovie.setPicture(newMovieDto.getPicture());
            newMovie.setTrailer(newMovieDto.getTrailer());
            return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie successfully updated"),
            @ApiResponse(responseCode = "400", description = "Malformed request"),
            @ApiResponse(responseCode = "404", description = "No movie found with that id"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    @PutMapping("{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody MoviePostDTO movieDto) {
        try {
            Movie updatedMovie = movieService.updateMovie(id, movieDto);
            return ResponseEntity.ok(updatedMovie);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Malformed request"),
            @ApiResponse(responseCode = "404", description = "No movie found with that id"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        MovieDTO movie = getSpecificMovie(id).getBody();
        String title = movie.getTitle();
        try {
            movieService.deleteMovie(id);
            return new ResponseEntity<>(title + " deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update the list of characters that appear in a given movie.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Updated successfully, no content to return"),
            @ApiResponse(responseCode = "400", description = "Bad request, please follow the API documentation for the proper request format"),
            @ApiResponse(responseCode = "404", description = "Not found, the specified movie or characters could not be found"),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
    })
    @PutMapping("{id}/characters")
    public ResponseEntity<Void> updateCharacters(@PathVariable Long id, @RequestBody int[] charactersIds) {
        movieService.updateCharacters(id, charactersIds);
        return ResponseEntity.noContent().build();
    }

}
