package com.J0175043.estimation.individualEstimate;

import com.J0175043.estimation.Estimate;
import com.J0175043.estimation.PersonState;
import com.J0175043.estimation.Time;
import com.J0175043.estimation.developer.Developer;
import com.J0175043.estimation.estimationConsortium.EstimationConsortium;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * The estimate of one developer.
 */
@Entity
final public class IndividualEstimate extends Estimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean unknown;
    
    @ManyToOne(targetEntity = EstimationConsortium.class)
    private EstimationConsortium estimationConsortium;

    @ManyToOne(targetEntity = Developer.class)
    private Developer estimator;

    public IndividualEstimate() {
    }

    /**
     * A constructor for a know time.
     * 
     * @throws Exception when Developer is not active.
     */
    public IndividualEstimate(Time time, Developer estimator) throws Exception {
        super(time);

        // Ensure the estimate is not marked as unknown so we have a record that the developer guessed an estimate.
        unknown = false;
        setEstimator(estimator);
    }

    /**
     * A constructor that can be used when a developer doesn't know an estimate.
     * 
     * @throws Exception when Developer is not active.
     */
    public IndividualEstimate(Developer estimator) throws Exception {
        // Set the time to zero to avoid as there is no known estimate.
        super(new Time(0));

        // Ensure the estimate is marked as unknown so we have a record that the developer did not guess an estimate.
        unknown = true;
        setEstimator(estimator);
    }

    /**
     * If the developer knew the estimate.
     */
    public boolean isUnknown() {
        return unknown;
    }

    /**
     * The developer who decided on this estimate.
     */
    public Developer getEstimator() {
        return estimator;
    }

    public EstimationConsortium getEstimationConsortium() {
        return estimationConsortium;
    }

    /**
     * Method to ensure behaviour for checking estimator is active is used reused in constructors.
     * 
     * Method is private to stop other classes overriding estimator.
     * 
     * @throws Exception when Developer is not active.
     */
    private void setEstimator(Developer estimator) throws Exception {
        // Developers must be active to add an estimate, throw exception if not.
        if (estimator.getState() != PersonState.Active) {
            throw new Exception(
                // Exception message contains username, and current and expected state to assist with debugging.
                String.format(
                    "Developer with username '%s' is state '%s' must be '%s'",
                    estimator.getUsername(),
                    estimator.getState(),
                    PersonState.Active
                )
            );
        }

        this.estimator = estimator;
    }
}
