package com.vdtas.models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * @author vvandertas
 */
public class UserTaskData {

    private UUID sessionId;
    private int taskId;

    private int queryCount =0;
    private int attempts = 0;
    private boolean answerFound = false;

    private Timestamp createdAt = new Timestamp(new Date().getTime());
    private Timestamp finishedAt;

    public UserTaskData() {
    }

    public UserTaskData(UUID sessionId, int taskId) {
        this.sessionId = sessionId;
        this.taskId = taskId;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(int queryCount) {
        this.queryCount = queryCount;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public boolean isAnswerFound() {
        return answerFound;
    }

    public void setAnswerFound(boolean answerFound) {
        this.answerFound = answerFound;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Timestamp finishedAt) {
        this.finishedAt = finishedAt;
    }
}
