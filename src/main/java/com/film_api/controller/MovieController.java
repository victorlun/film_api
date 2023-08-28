package com.film_api.controller;

import com.film_api.mapper.MovieMapper;
import com.film_api.model.dto.MovieCharacterDTO;
import com.film_api.model.dto.MovieDTO;
import com.film_api.model.Movie;
import com.film_api.model.dto.MoviePostDTO;
import com.film_api.service.CharacterService;
import com.film_api.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/movies")
public class MovieController {
    private final MovieService movieService;
    private final CharacterService characterService;
    @Autowired
    public MovieController(MovieService movieService, CharacterService characterService){
        this.movieService = movieService;
        this.characterService = characterService;
    }

    @Operation(summary = "Get all movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies successfully parsed"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Bad Request")
    })
    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAll(){
        List<MovieDTO> movies = movieService.getAllMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @Operation(summary = "Get specific movie")
    @GetMapping("{id}")
    public ResponseEntity<MovieDTO> getSpecificMovie(@PathVariable Long id) {
        return movieService.getMovieById(id)
                .map(movieDTO -> new ResponseEntity<>(movieDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Get all characters in a movie")
    @GetMapping("/movies/{movieId}/characters")
    public ResponseEntity<List<MovieCharacterDTO>> getAllCharactersByMovie(@PathVariable Long movieId) {
        List<MovieCharacterDTO> characters = characterService.getAllCharactersByMovie(movieId);
        return new ResponseEntity<>(characters, HttpStatus.OK);
    }



    @Operation(summary = "Post a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
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
            @ApiResponse(responseCode = "204", description = "Movie successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Malformed request"),
            @ApiResponse(responseCode = "404", description = "No movie found with that id")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete (@PathVariable Long id){
        MovieDTO movie = getSpecificMovie(id).getBody();
        String title = movie.getTitle();
        try {
            movieService.deleteMovie(id);
            return new ResponseEntity<>(title +" deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update the list of characters that appear in a given movie.")
    @PutMapping("{id}/characters")
    public ResponseEntity<Void> updateCharacters(@PathVariable Long id, @RequestBody int[] charactersIds) {
        movieService.updateCharacters(id, charactersIds);
        return ResponseEntity.noContent().build();
    }

}
