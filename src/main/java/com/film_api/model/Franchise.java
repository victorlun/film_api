package com.film_api.model;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "franchise")
public class Franchise {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column(name = "name")
        private String name;

        @Column(name = "description")
        private String description;

        @OneToMany(mappedBy="franchise")
        private Set<Movie> movies;

        public Franchise() {

        }

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

        @Override
        public String toString() {
            return "Character [id=" + id + ", name=" + name + ", description=" + description + "]";
        }
    }


