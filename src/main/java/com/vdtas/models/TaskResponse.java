package com.vdtas.models;

/**
 * @author vvandertas
 */
public class TaskResponse {
    private final int taskId;
    private final String url;
    private final String text;

    public TaskResponse(int taskIndex, String url, String text) {
        this.taskId = taskIndex;
        this.url = url;
        this.text = text;
    }

    public int getTaskIndex() {
        return taskId;
    }

    public String getUrl() {
        return url;
    }

    public String getText() {
        return text;
    }
}
