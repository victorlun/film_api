package com.film_api.model;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "release_year")
    private String releaseYear;

    @Column(name = "director")
    private String director;

    @Column(name = "picture")
    private String picture;

    @Column(name = "trailer")
    private String trailer;

    @ManyToMany(mappedBy = "playedInMovies")
    Set<MovieCharacter> charactersInMovie;

    @ManyToOne
    @JoinColumn(name="franchise_id", nullable=true)
    private Franchise franchise;

    public Movie() {

    }

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
}
