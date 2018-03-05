package com.vdtas.models;

import com.vdtas.models.participants.ParticipantType;

import java.util.HashMap;
import java.util.Map;
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
    private HashMap<String, Integer> taskCounts;


    public UserSession(UUID id, ParticipantType pt) {
        this.id = id;
        participantType = pt;
        remainingTasks = new Tasks();
        taskCounts = new HashMap<>();
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

    /**
     * Increment the query count for the current task
     *
     * @return incremented count or -1 if there is no current task
     */
    public int incrementQueryCount() {
        if(currentTask == null) {
            return -1;
        }
        return taskCounts.merge(currentTask.getId() +":queryCount", 1, Integer::sum);
    }

    /**
     * Increment the submission count for the current task
     *
     * @return incremented count or -1 if currentTask is null
     */
    public int incrementSubmisionCount() {
        if(currentTask == null) {
            return  -1;
        }

        return taskCounts.merge(currentTask.getId() +":submissionCount", 1, Integer::sum);
    }
}
