package com.film_api.model;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "franchise")
@Schema(description = "Represents a franchise.")
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

    /**
     * Default constructor.
     */
    public Franchise() {
    }

    /**
     * Constructor to create a new franchise.
     *
     * @param name        Name of the franchise. (Example: "Star Wars")
     * @param description Description of the franchise. (Example: "A space opera franchise")
     */
    public Franchise(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setName(String newName) {
        this.name = newName;
    }
    public String getName() {
        return name;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public String getDescription() {
        return description;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "Character [id=" + id + ", name=" + name + ", description=" + description + "]";
    }
}


