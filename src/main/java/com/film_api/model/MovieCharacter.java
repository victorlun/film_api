package com.film_api.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

// Auto-generated getters and setters
@Getter
@Setter
@Entity
@Table(name = "character")
public class MovieCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long character_id;

    @Column(name = "full_name")
    @Getter
    private String name;

    @Column(name = "alias")
    private String alias;

    @Column(name = "gender")
    private String gender;

    @Column(name = "url_photo")
    private String photo;

    @ManyToMany
    @JoinTable(
            name = "characters_movies",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    Set<Movie> playedInMovies;

    public MovieCharacter() {

    }

    public MovieCharacter(String name, String alias, String gender, String url) {
        this.name = name;
        this.alias = alias;
        this.gender = gender;
        this.photo = url;
    }

    public long getId() {
        return character_id;
    }

    public void setFullName(String newName) {
        this.name = newName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String newAlias) {
        this.alias = newAlias;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String newGender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String newUrl) {
        this.photo = newUrl;
    }

    @Override
    public String toString() {
        return "Character [id=" + character_id + ", name=" + name + ", alias=" + alias + ", gender=" + gender + ", photo url=" + photo + "]";
    }
}
