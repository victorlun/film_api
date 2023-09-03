package com.film_api.models.entities;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Entity
@Table(name = "movie")
@Schema(description = "Represents a movie.")
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID of the movie.", example = "1")
    private long id;

    @Column(name = "title")
    @Schema(description = "Title of the movie.", example = "Spider-man: Homecoming")
    private String title;

    @Column(name = "genre")
    @Schema(description = "Genre of the movie.", example = "Sci-fi")
    private String genre;

    @Column(name = "release_year")
    @Schema(description = "Release year of the movie.", example = "2012")
    private String releaseYear;

    @Column(name = "director")
    @Schema(description = "Director of the movie.", example = "Lana Wachowski")
    private String director;

    @Column(name = "picture")
    @Schema(description = "URL to the movie's picture.", example = "https://example.com/movie.jpg")
    private String picture;

    @Column(name = "trailer")
    @Schema(description = "URL to the movie's trailer.", example = "https://example.com/trailer.mp4")
    private String trailer;

    @ManyToMany(fetch = FetchType.EAGER) //cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Schema(description = "Characters in the movie.")
    @ArraySchema(schema = @Schema(implementation = MovieCharacter.class))
    @JoinTable(
            name = "characters_movies",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id"))
    Set<MovieCharacter> charactersInMovie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "franchise_id", nullable = true)
    @Schema(description = "Franchise to which the movie belongs.")
    private Franchise franchise;


    public void setFranchise(Franchise franchise) {
        // Handle the reverse relationship
        if (this.franchise != null) {
            this.franchise.getMovies().remove(this);
        }

        this.franchise = franchise;

        // Handle the forward relationship
        if (franchise != null && !franchise.getMovies().contains(this)) {
            franchise.getMovies().add(this);
        }
    }

    public void setCharacters(Set<MovieCharacter> characters) {
        this.charactersInMovie.clear();
        System.out.println("Inside setCharacters method in movie");
        if (characters != null) {
            this.charactersInMovie.addAll(characters);
        }
    }
}
