package com.film_api.mappers;

import com.film_api.models.dtos.franchise.FranchiseDTO;
import com.film_api.models.dtos.franchise.FranchisePostDTO;
import com.film_api.models.entities.Franchise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public abstract class FranchiseMapper {
    public abstract Franchise franchisePostDtoToFranchise(FranchisePostDTO franchisePostDTO);
    @Mapping(source = "name", target = "name")
    public abstract FranchiseDTO franchiseToFranchiseDTO(Franchise franchise);

    public abstract Franchise franchiseDTOtoFranchise(FranchiseDTO franchiseDTO);
}
