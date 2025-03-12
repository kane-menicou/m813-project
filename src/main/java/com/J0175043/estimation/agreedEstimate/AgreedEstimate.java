package com.J0175043.estimation.agreedEstimate;

import com.J0175043.estimation.Estimate;
import com.J0175043.estimation.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a time agreed by an EstimateConsortium.
 */
@Entity
final public class AgreedEstimate extends Estimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public AgreedEstimate () {
    }

    public AgreedEstimate(Time time) {
        super(time);
    }
}
