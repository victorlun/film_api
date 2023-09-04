package com.film_api.services.franchise;

import com.film_api.models.entities.Movie;
import com.film_api.mappers.FranchiseMapper;
import com.film_api.models.entities.Franchise;
import com.film_api.repositories.FranchiseRepository;
import com.film_api.repositories.MovieRepository;
import com.film_api.utils.exceptions.FranchiseNotFoundException;
import com.film_api.utils.exceptions.MovieNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class FranchiseServiceImpl implements FranchiseService {
    private final FranchiseRepository franchiseRepository;
    private final MovieRepository movieRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private FranchiseMapper franchiseMapper;

    @Autowired
    public FranchiseServiceImpl(FranchiseRepository franchiseRepository, MovieRepository movieRepository) {
        this.franchiseRepository = franchiseRepository;
        this.movieRepository = movieRepository;
    }
    @Override
    public List<Franchise> findAll() {
        return franchiseRepository.findAll();
    }
    @Override
    public Franchise findById(Long id) {
        return franchiseRepository.findById(id).orElseThrow(() -> new FranchiseNotFoundException(id));
    }

    @Override
    public Franchise add(Franchise franchise) {
        return franchiseRepository.save(franchise);
    }

    @Override
    public void update(Franchise franchise) {
        franchiseRepository.save(franchise);
    }

    @Transactional
    public void deleteById(Long id) {
        //Couldn't get it to work using the JPA-functionality
        Query nullifyFranchiseInMovieQuery = entityManager.createNativeQuery("UPDATE movie SET franchise_id = NULL WHERE franchise_id = ?")
                .setParameter(1, id);
        nullifyFranchiseInMovieQuery.executeUpdate();

        Query deleteFranchiseQuery = entityManager.createNativeQuery("DELETE FROM franchise WHERE id = ?")
                .setParameter(1, id);
        deleteFranchiseQuery.executeUpdate();
    }

    @Transactional
    public void updateFranchiseRelation(Long franchiseId, int[] movieIds) {
        // Find the franchise by ID or throw an exception if not found
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new FranchiseNotFoundException(franchiseId));
        // Create a set to store the new movies
        Set<Movie> movies = new HashSet<>();
        // Fetch movies by their IDs and add them to the set
        for (int id : movieIds) {
            Movie movie = movieRepository.findById((long) id)
                    .orElseThrow(() -> new MovieNotFoundException(id));
            movies.add(movie);
        }
        // Update both sides of the relationship
        franchise.setMovies(movies);
        movies.forEach(movie -> movie.setFranchise(franchise));

        // Save the updated franchise
        franchiseRepository.save(franchise);
    }


}
