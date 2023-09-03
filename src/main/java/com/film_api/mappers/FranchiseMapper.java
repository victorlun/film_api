package com.film_api.mappers;

import com.film_api.models.dtos.franchise.FranchiseDTO;
import com.film_api.models.entities.Franchise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface FranchiseMapper {

    @Mapping(source = "name", target = "name")
    FranchiseDTO franchiseToFranchiseDTO(Franchise franchise);

    Franchise franchiseDTOtoFranchise(FranchiseDTO franchiseDTO);
}
