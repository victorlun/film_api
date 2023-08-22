package com.film_api.service;

import com.film_api.dto.FranchiseDTO;
import com.film_api.mapper.FranchiseMapper;
import com.film_api.model.Franchise;
import com.film_api.repository.FranchiseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FranchiseService {
    private final FranchiseRepository franchiseRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private FranchiseMapper franchiseMapper;

    @Autowired
    public FranchiseService(FranchiseRepository franchiseRepository){
    this.franchiseRepository = franchiseRepository;
    }

    public List<FranchiseDTO> getAllFranchises(){
        List<Franchise> franchises = franchiseRepository.findAll();

        FranchiseDTO franchiseDTOConverter = new FranchiseDTO();  // Create an instance to call non-static convertToDTO()

        return franchises.stream()
                .map(franchiseDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }
    public Optional<Franchise> getSpecificFranchise(Long id){
        return  franchiseRepository.findById(id);
    }
    public Franchise createFranchise(Franchise franchise){
        return franchiseRepository.save(franchise);
    }

    public Franchise updateFranchise(Long id, Franchise franchiseDetails){
        Optional<Franchise> optionalFranchise = franchiseRepository.findById(id);

        if (optionalFranchise.isPresent()){
            Franchise franchise = optionalFranchise.get();
            franchise.setName(franchiseDetails.getName());
            franchise.setDescription(franchiseDetails.getDescription());
            return franchiseRepository.save(franchise);
        }else{
            throw new RuntimeException("Franchise not found with id: " + id);
        }
    }
    /*
    public void deleteFranchise(Long id){
        if(franchiseRepository.existsById(id)){
            franchiseRepository.deleteById(id);
        }else{
            throw new RuntimeException("Franchise not found with ID: " + id);
        }
    }
     */

    @Transactional
    public void deleteFranchise(Long id) {
        Query nullifyFranchiseInMovieQuery = entityManager.createNativeQuery("UPDATE movie SET franchise_id = NULL WHERE franchise_id = ?")
                .setParameter(1, id);
        nullifyFranchiseInMovieQuery.executeUpdate();

        Query deleteFranchiseQuery = entityManager.createNativeQuery("DELETE FROM franchise WHERE id = ?")
                .setParameter(1, id);
        deleteFranchiseQuery.executeUpdate();
    }

    public List<Franchise> getFranchiseByName(String name){
        return franchiseRepository.findByName(name);
    }
    }
