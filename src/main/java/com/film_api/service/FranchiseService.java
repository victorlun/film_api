package com.film_api.service;

import com.film_api.model.movie.Movie;
import com.film_api.model.franchise.FranchiseDTO;
import com.film_api.mapper.FranchiseMapper;
import com.film_api.model.franchise.Franchise;
import com.film_api.model.franchise.FranchisePostDTO;
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

    public FranchisePostDTO createFranchise(FranchisePostDTO franchiseDetails) {
        // Create a new Franchise entity from the incoming FranchisePostDTO
        Franchise newFranchise = new Franchise();
        newFranchise.setName(franchiseDetails.getName());
        newFranchise.setDescription(franchiseDetails.getDescription());

        // Save the new Franchise entity
        Franchise savedFranchise = franchiseRepository.save(newFranchise);

        // Convert the saved Franchise entity to a FranchisePostDTO
        FranchisePostDTO newFranchiseDTO = new FranchisePostDTO();
        newFranchiseDTO.setName(savedFranchise.getName());
        newFranchiseDTO.setDescription(savedFranchise.getDescription());

        return newFranchiseDTO;
    }

    public FranchisePostDTO updateFranchise(Long id, FranchisePostDTO franchiseDetails) {
        Franchise existingFranchise = franchiseRepository.findById(id).orElseThrow(() -> new RuntimeException("Franchise not found"));

        existingFranchise.setName(franchiseDetails.getName());
        existingFranchise.setDescription(franchiseDetails.getDescription());

        Franchise updatedFranchise = franchiseRepository.save(existingFranchise);

        // Convert Franchise entity to FranchisePostDTO before returning
        FranchisePostDTO updatedFranchiseDTO = new FranchisePostDTO();
        updatedFranchiseDTO.setName(updatedFranchise.getName());
        updatedFranchiseDTO.setDescription(updatedFranchise.getDescription());

        return updatedFranchiseDTO;
    }

    @Transactional
    public void updateFranchiseRelation(Long franchiseId, int[] movieIds) {

        // Find the franchise by ID or throw an exception if not found
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new RuntimeException("Franchise not found"));

        // Create a set to store the new movies
        Set<Movie> movies = new HashSet<>();

        // Fetch movies by their IDs and add them to the set
        for (int id : movieIds) {
            Movie movie = movieRepository.findById((long) id)
                    .orElseThrow(() -> new RuntimeException("Movie not found"));
            movies.add(movie);
        }

        // Update both sides of the relationship
        franchise.setMovies(movies);
        movies.forEach(movie -> movie.setFranchise(franchise));

        // Save the updated franchise
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
