package com.J0175043.estimation.individualEstimate;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.J0175043.estimation.estimationConsortium.EstimationConsortiumService;

@RestController
@RequestMapping("/api/individualEstimate")
class IndividualEstimateApiController {

    @Autowired
    IndividualEstimateService service;
    @Autowired
    EstimationConsortiumService estimationConsortiumService;
    @Autowired
    IndividualEstimateRepository repository;

    @DeleteMapping("{id}")
    public ResponseEntity<IndividualEstimate> getById(@PathVariable("id") Long id) {
        IndividualEstimate individualEstimate = repository.findById(id);
        if (individualEstimate == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        service.deleteIndividualEstimate(individualEstimate);

        return new ResponseEntity<>(individualEstimate, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<IndividualEstimate> create(@RequestBody IndividualEstimate individualEstimate) {
        try {
            IndividualEstimate savedItem = estimationConsortiumService.addIndividualEstimate(
                individualEstimate.getEstimationConsortium(), 
                individualEstimate.getTime(),
                individualEstimate.isUnknown(),
                individualEstimate.getEstimator()
            );
            return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<IndividualEstimate> update(@PathVariable("id") Long id, @RequestBody IndividualEstimate individualEstimate) {
        Optional<IndividualEstimate> exisitngIndividualEstimate = service.findById(id);
        if (exisitngIndividualEstimate.isPresent()) {
            IndividualEstimate existingItem = service.updateIndividualEstimate(individualEstimate);

            return new ResponseEntity<>(existingItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}