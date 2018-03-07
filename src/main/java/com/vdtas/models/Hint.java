package com.vdtas.models;

/**
 * @author vvandertas
 */
public class Hint {
    private int id;
    private int taskId;
    private String hint;

    public Hint() {
    }

    public Hint(int id, int taskId, String hint) {
        this.id = id;
        this.taskId = taskId;
        this.hint = hint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
