package com.film_api.service;

import com.film_api.model.Franchise;
import com.film_api.repository.FranchiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FranchiseService {
    private final FranchiseRepository franchiseRepository;

    @Autowired
    public FranchiseService(FranchiseRepository franchiseRepository){
    this.franchiseRepository = franchiseRepository;
    }

    public List<Franchise> getAllFranchises(){
        return franchiseRepository.findAll();
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
    public void deleteFranchise(Long id){
        if(franchiseRepository.existsById(id)){
            franchiseRepository.deleteById(id);
        }else{
            throw new RuntimeException("Franchise not found with ID: " + id);
        }
    }

    public List<Franchise> getFranchiseByName(String name){
        return franchiseRepository.findByName(name);
    }
    }
