package com.film_api.model.movie_character;
import com.film_api.model.movie.Movie;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

// Auto-generated getters and setters
@Getter
@Setter
@Entity
@Table(name = "character")
@Schema(description = "Represents a movie character.")
public class MovieCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID of the character.", example = "1")
    private long character_id;

    @Column(name = "full_name")
    @Schema(description = "Full name of the character.", example = "Tony Stark")
    private String name;

    @Column(name = "alias")
    @Schema(description = "Alias of the character.", example = "Iron Man")
    private String alias;

    @Column(name = "gender")
    @Schema(description = "Gender of the character.", example = "Male")
    private String gender;

    @Column(name = "url_photo")
    @Schema(description = "Url of photo of character.", example = "https://en.wikipedia.org/wiki/Iron_Man_(2008_film)#/media/File:Iron_Man_(2008_film)_poster.jpg")
    private String photo;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "charactersInMovie")
    @Schema(description = "Set of movies the character has played in.")
    Set<Movie> playedInMovies;

    /**
     * Default constructor.
     */
    public MovieCharacter() {
    }

    /**
     * Constructor to create a new movie character.
     *
     * @param name   Full name of the character. (Example: "John Doe")
     * @param alias  Alias or nickname of the character. (Example: "The Hero")
     * @param gender Gender of the character. (Example: "Male")
     * @param url    URL to the character's photo. (Example: "https://example.com/character.jpg")
     */
    public MovieCharacter(String name, String alias, String gender, String url) {
        this.name = name;
        this.alias = alias;
        this.gender = gender;
        this.photo = url;
    }

    @Override
    public String toString() {
        return "Character [id=" + character_id + ", name=" + name + ", alias=" + alias + ", gender=" + gender + ", photo url=" + photo + "]";
    }
}
