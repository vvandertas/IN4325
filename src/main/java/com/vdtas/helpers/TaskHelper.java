package com.vdtas.helpers;

import com.vdtas.SessionException;
import com.vdtas.daos.TaskDao;
import com.vdtas.daos.UserDao;
import com.vdtas.models.Task;
import com.vdtas.models.User;

import javax.inject.Inject;

/**
 * @author vvandertas
 */
public class TaskHelper {
    protected static final int maxTaskId = 4; // TODO: Verify

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
     * @throws SessionException
     */
    public Task getNextTask(User user) {
        Task nextTask = null;
        int currentTaskId = user.getCurrentTaskId();

        if(0 <= currentTaskId  && currentTaskId < maxTaskId) {
            user.setCurrentTaskId(++currentTaskId);
            userDao.updateTaskId(user.getId(), currentTaskId);

            nextTask = taskDao.findById(currentTaskId);
        } else if (currentTaskId >= maxTaskId) {
            user.setCurrentTaskId(-1);
            userDao.updateTaskId(user.getId(), user.getCurrentTaskId());
        }

        return nextTask;
    }

    public Task findTask(int taskId) {
        return taskDao.findById(taskId);
    }



}
