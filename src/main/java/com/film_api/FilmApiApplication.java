package com.film_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaRepositories
@EntityScan(basePackages = {"com.project.film_api"})
public class FilmApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmApiApplication.class, args);
	}

}
