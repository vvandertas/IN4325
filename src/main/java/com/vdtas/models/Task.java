package com.vdtas.models;

/**
 * @author vvandertas
 */
public class Task {
    public static final String GENERIC_HINT = "The generic hint we show for all tasks";

    private int id;
    private String question;
    private String name;

    public Task() {
    }

    public Task(String question, String name) {
        this.question = question;
        this.name = name;
    }

    public Task(int id, String question, String name) {
        this.id = id;
        this.question = question;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}