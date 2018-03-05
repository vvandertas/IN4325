package com.vdtas.models;

/**
 * @author vvandertas
 */
public class TaskResponse {
    private final int taskIndex;
    private final String url;
    private final String text;

    public TaskResponse(int taskIndex, String url, String text) {
        this.taskIndex = taskIndex;
        this.url = url;
        this.text = text;
    }

    public int getTaskIndex() {
        return taskIndex;
    }

    public String getUrl() {
        return url;
    }

    public String getText() {
        return text;
    }
}
