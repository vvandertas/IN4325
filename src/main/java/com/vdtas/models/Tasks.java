package com.vdtas.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author vvandertas
 */
public class Tasks {
    // List of all tasks used in the experiment
    private final List<Task> tasks = new ArrayList<Task>() {{
        new Task("NAME", "My awesome question");
        new Task("NAME2", "My awesome question 2");
    }};

    private final Logger logger = LoggerFactory.getLogger(Tasks.class);

    public Tasks() { }

    /**
     * Get all (remaining) tasks
     *
     * @return
     */
    public List<Task> getTasks() {
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

    /**
     * Get a random task
     * @return
     */
    public Task randomNext() {
        if(tasks.isEmpty()) {
            logger.info("No tasks left.");
            return null;
        }

        int i = ThreadLocalRandom.current().nextInt(0, tasks.size());
        return tasks.remove(i);
    }

}