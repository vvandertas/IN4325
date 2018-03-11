package com.vdtas.models;

/**
 * @author vvandertas
 */
public class Keyword {
    private int taskId;
    private String keyword;

    public Keyword(int taskId, String keyword) {
        this.taskId = taskId;
        this.keyword = keyword;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
