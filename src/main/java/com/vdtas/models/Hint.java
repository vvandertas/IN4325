package com.vdtas.models;

/**
 * @author vvandertas
 */
public class Hint {
    private int id;
    private int taskId;
    private String hint;

    private HintType hintType;

    public Hint() {
    }

    public Hint(int taskId, String hint, HintType hintType) {
        this.taskId = taskId;
        this.hint = hint;
        this.hintType = hintType;
    }

    public Hint(int id, int taskId, String hint, HintType hintType) {
        this.id = id;
        this.taskId = taskId;
        this.hint = hint;
        this.hintType = hintType;
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

    public HintType getHintType() {
        return hintType;
    }

    public void setHintType(HintType hintType) {
        this.hintType = hintType;
    }
}
