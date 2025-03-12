package com.J0175043.estimation.developer;

import com.J0175043.estimation.PersonState;
import com.J0175043.estimation.Person;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a person within the organisation who estimates Tasks as part of an EstimationConsortium.
 */
@Entity
final public class Developer extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public Developer() {
    }

    public Developer (String name, String username, PersonState state) {
        super(name, username, state);
    }
}