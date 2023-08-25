package com.film_api.service;

import com.film_api.model.Movie;
import com.film_api.model.dto.FranchiseDTO;
import com.film_api.mapper.FranchiseMapper;
import com.film_api.model.Franchise;
import com.film_api.repository.FranchiseRepository;
import com.film_api.repository.MovieRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FranchiseService {
    private final FranchiseRepository franchiseRepository;
    private final MovieRepository movieRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private FranchiseMapper franchiseMapper;

    @Autowired
    public FranchiseService(FranchiseRepository franchiseRepository, MovieRepository movieRepository) {
        this.franchiseRepository = franchiseRepository;
        this.movieRepository = movieRepository;
    }

    public List<FranchiseDTO> getAllFranchises() {
        List<Franchise> franchises = franchiseRepository.findAll();

        FranchiseDTO franchiseDTOConverter = new FranchiseDTO();  // Create an instance to call non-static convertToDTO()

        return franchises.stream()
                .map(franchiseDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<FranchiseDTO> getSpecificFranchise(Long id) {
        Optional<Franchise> franchise = franchiseRepository.findById(id);

        FranchiseDTO franchiseDTOConverter = new FranchiseDTO();  // Create an instance to call non-static convertToDTO()

        return franchise.map(franchiseDTOConverter::convertToDTO);  // Convert to FranchiseDTO if present
    }

    public Franchise createFranchise(Franchise franchise) {
        return franchiseRepository.save(franchise);
    }

    public Franchise updateFranchise(Long id, Franchise franchiseDetails) {
        Optional<Franchise> optionalFranchise = franchiseRepository.findById(id);

        if (optionalFranchise.isPresent()) {
            Franchise franchise = optionalFranchise.get();
            franchise.setName(franchiseDetails.getName());
            franchise.setDescription(franchiseDetails.getDescription());
            return franchiseRepository.save(franchise);
        } else {
            throw new RuntimeException("Franchise not found with id: " + id);
        }
    }

    @Transactional
    public void updateFranchiseRelation(Long franchiseId, int[] movieIds) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new RuntimeException("Franchise not found"));

        Set<Movie> movies = new HashSet<>();
        for (int id : movieIds) {
            Movie movie = movieRepository.findById((long) id)
                    .orElseThrow(() -> new RuntimeException("Movie not found"));
            movies.add(movie);
        }

        // Update both sides of the relationship
        franchise.setMovies(movies);
        movies.forEach(movie -> movie.setFranchise(franchise));

        franchiseRepository.save(franchise);
    }

    @Transactional
    public void deleteFranchise(Long id) {
        Query nullifyFranchiseInMovieQuery = entityManager.createNativeQuery("UPDATE movie SET franchise_id = NULL WHERE franchise_id = ?")
                .setParameter(1, id);
        nullifyFranchiseInMovieQuery.executeUpdate();

        Query deleteFranchiseQuery = entityManager.createNativeQuery("DELETE FROM franchise WHERE id = ?")
                .setParameter(1, id);
        deleteFranchiseQuery.executeUpdate();
    }

    public List<Franchise> getFranchiseByName(String name) {
        return franchiseRepository.findByName(name);
    }
}
