package com.vdtas.helpers;

import com.vdtas.daos.TaskDao;
import com.vdtas.daos.UserDao;
import com.vdtas.models.Task;
import com.vdtas.models.TaskResponse;
import com.vdtas.models.User;

import javax.inject.Inject;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author vvandertas
 */
public class TaskHelper {

    private TaskDao taskDao;
    private UserDao userDao;

    @Inject
    public TaskHelper(TaskDao taskDao, UserDao userDao) {
        this.taskDao = taskDao;
        this.userDao = userDao;
    }

    /**
     * Based on the remaining tasks for a user session, randomly select the next task
     * and update the current task for this user session.
     * <p>
     * The task returned is null if the user has completed all tasks in the experiment
     *
     * @param user Current user
     * @return
     */
    public boolean getNextTask(User user) {
        boolean hasNext = false;
        int currentTaskId = user.getCurrentTaskId();

        Task nextTask = taskDao.findById(currentTaskId + 1);

        if(nextTask != null) {
            hasNext = true;
            user.setCurrentTaskId(++currentTaskId);
            userDao.updateTaskId(user.getId(), currentTaskId);

        } else if (currentTaskId != -1) {
            user.setCurrentTaskId(-1);
            userDao.updateTaskId(user.getId(), user.getCurrentTaskId());
        }

        return hasNext;
    }

    public Task findTask(int taskId) {
        return taskDao.findById(taskId);
    }

    public boolean validateTask(TaskResponse taskResponse) {
        List<String> keywordsForTask = taskDao.findKeywordsForTask(taskResponse.getTaskId());

        // TODO: Do something with submitted url

        // Validate content of answer using a regular expression
        String regex = String.join("", keywordsForTask.stream().map(s -> "(?=.*" + s + ")").collect(Collectors.toList()));
        Pattern pattern = Pattern.compile(regex);

        if(pattern.matcher(taskResponse.getText().toLowerCase()).find()){
            return true;
        } else {
            return false;
        }
    }

}
