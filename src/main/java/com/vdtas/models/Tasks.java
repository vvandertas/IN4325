package com.vdtas.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vvandertas
 */
public class Tasks {
    // List of all tasks used in the experiment
    private static final List<Task> tasks = new ArrayList<Task>() {{
        new Task(1, "Question 1", "NAME");
        new Task(2,"Question 2", "NAME2");
    }};

    private final Logger logger = LoggerFactory.getLogger(Tasks.class);

    public Tasks() { }

    /**
     * Get all (remaining) tasks
     *
     * @return
     */
    public static List<Task> getTasks() {
        return tasks;
    }

    /**
     * Get the top Task of the stack as next Task
     * @return
     */
    public Task next() {
        if(tasks.isEmpty()) {
            logger.info("No tasks left.");
            return null;
        }
        return tasks.remove(0);
    }
}