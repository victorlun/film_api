package com.film_api.model;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "movie")
@Schema(description = "Represents a movie.")
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

    @ManyToOne
    @JoinColumn(name = "franchise_id", nullable = true)
    @Schema(description = "Franchise to which the movie belongs.")
    private Franchise franchise;

    /**
     * Default constructor.
     */
    public Movie() {
    }

    /**
     * Constructor to create a new movie.
     *
     * @param title       Title of the movie. (Example: "The Matrix")
     * @param genre       Genre of the movie. (Example: "Science Fiction")
     * @param releaseYear Release year of the movie. (Example: "1999")
     * @param director    Director of the movie. (Example: "Lana Wachowski")
     * @param picture     URL to the movie's picture. (Example: "https://example.com/movie.jpg")
     * @param trailer     URL to the movie's trailer. (Example: "https://example.com/trailer.mp4")
     */
    public Movie(String title, String genre, String releaseYear, String director, String picture, String trailer) {
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.director = director;
        this.picture = picture;
        this.trailer = trailer;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String newGenre) {
        this.genre = newGenre;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String newReleaseYear) {
        this.releaseYear = newReleaseYear;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String newDirector) {
        this.director = newDirector;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String newPicture) {
        this.picture = newPicture;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String newTrailer) {
        this.trailer = newTrailer;
    }

    @Override
    public String toString() {
        return "Character [id=" + id + ", title=" + title + ", genre=" + genre + ", release_year=" + releaseYear + ", director=" + director + ", picture url=" + picture + ", trailer=" + trailer + "]";
    }

    public void setCharacters(Set<MovieCharacter> characters) {
        this.charactersInMovie.clear();
        System.out.println("Inside setCharacters method in movie");
        if (characters != null) {
            this.charactersInMovie.addAll(characters);
        }
    }
}
