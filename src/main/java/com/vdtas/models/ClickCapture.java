package com.vdtas.models;

import java.util.UUID;

/**
 * @author vvandertas
 */
public class ClickCapture {
    private UUID userId;
    private String url;
    private int taskId;


    public ClickCapture() {
    }

    public ClickCapture(String url, int taskId) {
        this.url = url;
        this.taskId = taskId;
    }

    public ClickCapture(UUID userId, String url, int taskId) {
        this.userId = userId;
        this.url = url;
        this.taskId = taskId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "ClickCapture{" +
                "userId=" + userId +
                ", url='" + url + '\'' +
                ", taskId=" + taskId +
                '}';
    }
}
