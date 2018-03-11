package com.vdtas.models;

import java.util.UUID;

/**
 * @author vvandertas
 */
public class TaskResponse {
    private int taskId;
    private UUID userId;
    private String url;
    private String text;


    public TaskResponse(int taskId, String url, String text) {
        this.taskId = taskId;
        this.url = url;
        this.text = text;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getUrl() {
        return url;
    }

    public String getText() {
        return text;
    }
}
