package com.vdtas.models;

/**
 * @author vvandertas
 */
public class Task {
    public static final String GENERIC_HINT = "The generic hint we show for all tasks";

    private final String genericHint;
    private final int id;
    private String name;
    private String question;
    private String specificHint;

    public Task(int id, String name, String question) {
        this.id = id;
        this.name = name;
        this.question = question;
        this.genericHint = "The generic hint we show for all tasks";
    }

    public Task(int id, String name, String question, String specificHint) {
        this.id = id;
        this.name = name;
        this.question = question;
        this.specificHint = specificHint;
        this.genericHint = "The generic hint we show for all tasks";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSpecificHint() {
        return specificHint;
    }

    public void setSpecificHint(String specificHint) {
        this.specificHint = specificHint;
    }

    public String getGenericHint() {
        return genericHint;
    }
}