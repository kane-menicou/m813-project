package com.J0175043.estimation.task;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a task which is being estimated.
 */
@Entity
final public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * A string used to identify the Task in a third party system.
     */
    private String taskCode;

    public Task() {
    }

    public Task (String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskCode() {
        return taskCode;
    }
}
