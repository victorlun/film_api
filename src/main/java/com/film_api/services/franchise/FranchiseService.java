package com.film_api.services.franchise;

import com.film_api.models.entities.Franchise;
import com.film_api.services.CrudService;

public interface FranchiseService extends CrudService<Franchise, Long> {
    void updateFranchiseRelation(Long franchiseId, int[] movieIds);
}
