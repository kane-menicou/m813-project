package com.J0175043.estimation.estimationConsortium;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.J0175043.estimation.Time;
import com.J0175043.estimation.agreedEstimate.AgreedEstimate;
import com.J0175043.estimation.developer.Developer;
import com.J0175043.estimation.individualEstimate.IndividualEstimate;

import jakarta.persistence.EntityManager;

@Service
final public class EstimationConsortiumService {
    final private static long MILLIS_IN_SECOND = 1_000;

    @Autowired
    private EntityManager entityManager;

    public void addAgreedEstimate(EstimationConsortium estimationConsortium, Time time) throws Exception {        
        if (
            // Every member must have provided an Estimate to add an AgreedEstimate.
            estimationConsortium.getIndividualEstimates().size() != estimationConsortium.getMembers().size()
            // or if the max time has been reached
            && hasMaxSecondsBeenReached(estimationConsortium)
        ) {
            throw new Exception("EstimationConsortium is not competed yet");
        }

        // Create new AgreedEstimate with time passed.
        AgreedEstimate estimate = new AgreedEstimate(time);

        // Set the AgreedEstimate for the EstimationConsortium
        estimationConsortium.setAgreedEstimate(estimate);

        // Persist to ensure the EntityManager is aware of the new AgreedEstimate.
        entityManager.persist(estimate);

        // Save the new AgreedEstimate to the database, and ensure any changes to the EstimationConsortium are saved.
        entityManager.flush();
    }

    /**
     * @throws Exception when Developer is not a member of this EstimationConsortium.
     * @throws Exception when Developer attempts to add another IndividualEstimate.
     * @throws Exception when Developer is not active.
     * 
     * Add a new Estimate from a Developer.
     */
    public IndividualEstimate addIndividualEstimate(EstimationConsortium estimationConsortium, Time time, boolean isUnknown, Developer developer) throws Exception {
        // Members who are not part of this EstimationConsortium cannot add IndividualEstimates. Throw an exception if attempting to add.
        if (! estimationConsortium.getMembers().contains(developer)) {
            throw new Exception(
                // Exception message contains username to assist with debugging.
                String.format(
                    "Developer with username '%s' is not a member of this EstimationConsortium",
                    developer.getUsername()
                )
            );
        }

        // Check the Developer is not trying to add another IndividualEstimate.
        if (hasDeveloperAlreadyProvidedEstimate(estimationConsortium, developer)) {
            throw new Exception(
                // Exception message contains username to assist with debugging.
                String.format(
                    "Developer with username '%s' has already provided an IndividualEstimate",
                    developer.getUsername()
                )
            );
        }

        // Can only add IndividualEstiamte when state is new or in progress.
        if (estimationConsortium.getState() != EstimationConsortiumState.New && estimationConsortium.getState() != EstimationConsortiumState.InProgress) {
            throw new Exception(
                // Exception message contains username to assist with debugging.
                String.format(
                    "Developer with username '%s' cannot add IndividualEstimate to EstimationConsortium in state '%s'",
                    developer.getUsername(),
                    developer.getState()
                )
            );
        }

        // We need to call the correct constructor for the IndividualEstimate depending on if the IndividualEstimate is known.
        IndividualEstimate estimate = isUnknown ?
            // The IndividualEstimate constructor for unknown estimates does not require a time.
            new IndividualEstimate(developer) : 
            // The IndividualEstimate constructor for known estimates requires both the time and developer.
            new IndividualEstimate(time, developer)
        ;

        // Store the new IndividualEstimate for this EstimationConsortium.
        estimationConsortium.addIndividualEstimate(estimate);

        // This EstimationConsortium is in progress once an IndividualEstimate has been added.
        estimationConsortium.setState(EstimationConsortiumState.InProgress);

        // Persist to ensure the EntityManager is aware of the new IndividualEstimate.
        entityManager.persist(estimate);

        // Save the new IndividualEstimate to the database, and ensure any changes to the EstimationConsortium are saved.
        entityManager.flush();

        return estimate;
    }

    /**
     * This is a private method for readability. It checks if max wait seconds have been reached.
     */
    private boolean hasMaxSecondsBeenReached(EstimationConsortium estimationConsortium) {
        // Convert maxWaitTimeSecond to milliseconds.
        long millisMaxWait = estimationConsortium.getMaxWaitSeconds() * MILLIS_IN_SECOND;

        // Add the createdAt time to max wait to calculate what the UNIX epoch ms is when the EstimationConsortium has reached max wait time.
        long millisSinceUnixEpochWhenMaxWaitReached = estimationConsortium.getCreatedAt().getTime() + millisMaxWait;

        // Has the max time been reached.
        return millisSinceUnixEpochWhenMaxWaitReached > System.currentTimeMillis();
    }


    /**
     * This is a private method for readability. It checks if Developer provided has already provided an estimate.
     */
    private boolean hasDeveloperAlreadyProvidedEstimate(EstimationConsortium estimationConsortium, Developer developer) {
        return estimationConsortium
            // Get all the individual estimates from the EstimationConsortium.  
            .getIndividualEstimates()
            // Convert to stream to add convience methods for accessing for current developer.
            .stream()
            // Filter estimates to only ones from developer currently trying to add estimate.
            .filter(item -> item.getEstimator() == developer)
            // To list so we can easily get size.
            .toList()
            // Check if there are any items (greater than zero count).
            .size() > 0
        ;
    }
}
