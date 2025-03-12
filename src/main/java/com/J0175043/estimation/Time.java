package com.J0175043.estimation;

/**
 * Data Transfer Object that is a offers methods for calculations of developer days.
 */
final public class Time {
    /**
     * Requirement collected directly from stakeholder during development. Represents the number of hours a developer is expected to do in a day.
     */
    public static final double HOURS_IN_DEVELOPER_DAYS = 5;

    /**
     * Number of hours in time. Minimum required graunularity for estimate.
     */
    private int hours;

    public Time (int hours) {
        this.hours = hours;
    }

    /**
     * Developer days are a concept within the organisation, they how many hours of work an 
     */
    public double getDeveloperDays() {
        return hours / HOURS_IN_DEVELOPER_DAYS;
    }

    /**
     * Represents total time!
     */
    public int getHours() {
        return hours;
    }
}
