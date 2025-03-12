package com.J0175043.estimation;

public enum PersonState {
    /**
     * Can perform all operations.
     */
    Active,
    /**
     * Can only read data from the system.
     */
    ReadOnly,

    /**
     * Nothing allowed, Person switched off.
     */
    Disabled,
}
