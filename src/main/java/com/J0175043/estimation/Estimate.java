package com.J0175043.estimation;

/**
 * Stores a time for a task decided by developers
 */
abstract public class Estimate {
    private Time time;

    public Estimate() {
    }

    public Estimate (Time time) {
        this.time = time;
    }

    final public void setTime(Time time) {
        this.time = time;
    }

    final public Time getTime() {
        return time;
    }
}
