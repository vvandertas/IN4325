package com.vdtas.models;

/**
 * @author vvandertas
 */
public class Task {
    int id;
    private String name;
    private String question;
    private String specificHint;
    private String genericHint;

    public Task(String name, String question) {
        this.name = name;
        this.question = question;
    }

    public Task(String name, String question, String specificHint, String genericHint) {
        this.name = name;
        this.question = question;
        this.specificHint = specificHint;
        this.genericHint = genericHint;
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

    public void setGenericHint(String genericHint) {
        this.genericHint = genericHint;
    }
}
