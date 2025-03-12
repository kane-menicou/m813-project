package com.J0175043.estimation.estimationConsortium;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.J0175043.estimation.agreedEstimate.AgreedEstimate;
import com.J0175043.estimation.developer.Developer;
import com.J0175043.estimation.individualEstimate.IndividualEstimate;
import com.J0175043.estimation.task.Task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

/**
 * Represents a group of Developers (members) estimating a Task.
 */
@Entity
final public class EstimationConsortium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The maximum time this EstimationConsortium can be state InProgress.
     */
    private int maxWaitTimeSeconds;

    /**
     * Current state of this EstimationConsortium.
     */
    private EstimationConsortiumState state = EstimationConsortiumState.New;

    /**
     * Task which this EstimationConsortium is estimating.
     */
    @OneToOne(targetEntity = Task.class)
    private Task task;

    /**
     * Developers which are part of (members) this EstimationConsortium.
     */
    @ManyToMany(targetEntity = Developer.class)
    private List<Developer> members;

    /**
     * List of the current IndividualEstimates of this EstimationConsortium. 
     */
    @OneToMany(targetEntity = IndividualEstimate.class, mappedBy = "estimationConsortium")
    private List<IndividualEstimate> individualEstimates;

    /**
     * The AgreedEstimate of this EstimateConsortium.
     */
    @OneToOne(targetEntity = Developer.class)
    private Optional<AgreedEstimate> agreedEstimate;

    /**
     * The date on which this EstimationConsortium was created.
     */
    private Date createdAt;

    public EstimationConsortium() {
    }

    public EstimationConsortium(Task task, int maxWaitTimeSeconds) {
        this.task = task;
        this.maxWaitTimeSeconds = maxWaitTimeSeconds;

        // Initialise empty ArrayLists to avoid null pointer errors.
        members = new ArrayList<Developer>();
        individualEstimates = new ArrayList<IndividualEstimate>();

        // Store no AgreedEstimate by default, using Optional.
        agreedEstimate = Optional.empty();

        // Store time createdAt time so we can check.
        createdAt = new Date(System.currentTimeMillis());
    }

    /**
     * Get the maximum time this EstimationConsortium can be state InProgress.
     */
    public int getMaxWaitSeconds() {
        return maxWaitTimeSeconds;
    }

    /**
     * Get the current state of this EstimationConsortium.
     */
    public EstimationConsortiumState getState() {
        return state;
    }

    /**
     * DO NOT USE IN IMPLEMENTATION, JUST USED FOR TESTS!
     *
     * Get the current state of this EstimationConsortium.
     */
    public void setState(EstimationConsortiumState state) {
        this.state = state;
    }

    /**
     * Get the Task which this EstimationConsortium is estimating.
     */
    public Task getTask() {
        return task;
    }

    /**
     * Set the Task which this EstimationConsortium is estimating.
     */
    @Column(nullable = false)
    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * Add a new Developer as a member of this EstimationConsortium.
     * 
     * WARNING: this is an incomplete method!
     */
    public void addMember(Developer member) {
        members.add(member);
    }

    /**
     * Get the Developers which are part of (members) this EstimationConsortium.
     */
    public List<Developer> getMembers() {
        return members;
    }

    public void addIndividualEstimate(IndividualEstimate individualEstimate) {
        individualEstimates.add(individualEstimate);
    }

    /**
     * Get the List of the current IndividualEstimates of this EstimationConsortium.
     */
    public List<IndividualEstimate> getIndividualEstimates() {
        return individualEstimates;
    }

    public Optional<AgreedEstimate> getAgreedEstimate() {
        return agreedEstimate;
    }

    /**
     * Added for tests so we can pretend the time is in the future. Avoid using in implementation.
     */
    @Column(nullable = false)
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Set the AgreedEstimate for this EstimationConsortium is estimating.
     */
    public void setAgreedEstimate(AgreedEstimate agreedEstimate) {
        this.agreedEstimate = Optional.of(agreedEstimate);
    }
}
