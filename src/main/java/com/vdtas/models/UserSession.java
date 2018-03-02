package com.vdtas.models;

import com.vdtas.models.participants.ParticipantType;

import java.util.UUID;

/**
 * @author vvandertas
 */
public class UserSession {

    private final UUID id;
    private final ParticipantType participantType;
    private int queryCount = 0;

    private Tasks remainingTasks;
    private Task currentTask;
    private int taskQueryCount;
    private int taskSubmissionCount;


    public UserSession(UUID id, ParticipantType pt) {
        this.id = id;
        participantType = pt;
        remainingTasks = new Tasks();
    }

    public UUID getId() {
        return id;
    }

    public int getQueryCount() {
        return queryCount;
    }

    public Tasks getRemainingTasks() {
        return remainingTasks;
    }

    public ParticipantType getParticipantType() {
        return participantType;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    /**
     * Increment and return the query count
     * @return
     */
    public int incrementCount() {
        return ++queryCount;
    }
}
