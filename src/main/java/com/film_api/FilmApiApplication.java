package com.film_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.film_api.repositories")
public class FilmApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilmApiApplication.class, args);
    }

}
