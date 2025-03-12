package com.J0175043.estimation.individualEstimate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;

@Service
public class IndividualEstimateService {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private IndividualEstimateRepository repository;

    public void deleteIndividualEstimate(IndividualEstimate individualEstimate) {
        entityManager.remove(individualEstimate);
        entityManager.flush();        
    }

    public IndividualEstimate updateIndividualEstimate(Long id, IndividualEstimate individualEstimate) {
        IndividualEstimate original = repository.findById(id);

        original.setTime(individualEstimate.getTime());

        entityManager.flush();

        return individualEstimate;
    }
}
