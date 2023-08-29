package com.film_api.mapper;

import com.film_api.model.franchise.FranchiseDTO;
import com.film_api.model.franchise.Franchise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface FranchiseMapper {

    @Mapping(source = "name", target = "name")
    FranchiseDTO franchiseToFranchiseDTO(Franchise franchise);

    Franchise franchiseDTOtoFranchise(FranchiseDTO franchiseDTO);
}
