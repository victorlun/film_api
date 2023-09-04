package com.film_api.models.entities;
import jakarta.persistence.*;

import io.swagger.v3.oas.annotations.media.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "franchise")
@Schema(description = "Represents a franchise.")
@Getter
@Setter
public class Franchise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID of the franchise.", example = "1")
    private long id;

    @Column(name = "name")
    @Schema(description = "Name of the franchise.", example = "Marvel")
    private String name;

    @Column(name = "description")
    @Schema(description = "Description of the franchise.", example = "A superhero franchise")
    private String description;

    @OneToMany(mappedBy = "franchise")
    @Schema(description = "Movies in the franchise.")
    @ArraySchema(schema = @Schema(implementation = Movie.class))
    private Set<Movie> movies;


    public void setMovies(Set<Movie> newMovies) {
        if (this.movies == null) {
            this.movies = new HashSet<>();
        }

        // Remove movies that are not in the newMovies set
        this.movies.retainAll(newMovies);

        // Add new movies from the newMovies set
        this.movies.addAll(newMovies);
    }
}






