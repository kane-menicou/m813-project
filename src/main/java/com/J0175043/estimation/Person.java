package com.J0175043.estimation;

/*
 * A representation of a person within the organisation.
 */
public class Person {
    /**
     * Name of the person.
     */
    private String name;

    /**
     * Unique identifier of the person.
     */
    private String username;

    /**
     * Represents the state of the person within the organsiation.
     */
    private PersonState state;

    public Person () {
    }

    public Person (String name, String username, PersonState state) {
        this.name = name;
        this.username = username;
        this.state = state;
    }

    final public String getName() {
        return name;
    }

    final public String getUsername() {
        return username;
    }

    final public PersonState getState() {
        return state;
    }
}
