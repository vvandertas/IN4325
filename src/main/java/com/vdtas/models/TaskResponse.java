package com.vdtas.models;

import java.util.UUID;

/**
 * @author vvandertas
 */
public class TaskResponse {
    private int taskId;
    private UUID userId;
    private String text;


    public TaskResponse(int taskId, String text) {
        this.taskId = taskId;
        this.text = text;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getText() {
        return text;
    }
}
