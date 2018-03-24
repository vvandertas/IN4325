package com.vdtas.models;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * @author vvandertas
 */
public class Task {
    public static final List<String> GENERIC_HINTS =
            ImmutableList.of("Split the question into 2 or more logical parts",
                    "Find answers to the parts of the question",
                    "Use answers to the parts of the question to find the answer to the full question");

    private int id;
    private String question;
    private String flags;
    private String name;

    public Task() {
    }

    public Task(String question, String flags, String name) {
        this.question = question;
        this.flags = flags;
        this.name = name;
    }

    public Task(int id, String question, String flags, String name) {
        this.id = id;
        this.question = question;
        this.flags = flags;
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

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
