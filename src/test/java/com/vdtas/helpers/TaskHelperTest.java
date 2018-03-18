package com.vdtas.helpers;

import com.google.common.collect.ImmutableList;
import com.vdtas.daos.TaskDao;
import com.vdtas.daos.UserDao;
import com.vdtas.models.Task;
import com.vdtas.models.TaskResponse;
import com.vdtas.models.User;
import com.vdtas.models.participants.ParticipantType;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author vvandertas
 */
public class TaskHelperTest {
    private int taskCount = 2;

    private TaskDao mockedTaskDao;
    private UserDao mockedUserDao;
    private TaskHelper taskHelper;

    @Before
    public void setup() {
        mockedUserDao = mock(UserDao.class);
        mockedTaskDao = mock(TaskDao.class);

        taskHelper = new TaskHelper(mockedTaskDao, mockedUserDao);
    }

    @Test
    public void nextTaskTest_withRemainingTasks() throws Exception {
        User user = new User(UUID.randomUUID(), ParticipantType.NOHINT);
        user.setCurrentTaskId(2);

        Task expectedTask = new Task(3, "Nice Question!", "with a name");
        when(mockedTaskDao.findById(user.getCurrentTaskId() + 1)).thenReturn(expectedTask);

        boolean hasNext = taskHelper.getNextTask(user);
        assertTrue(hasNext);

        verify(mockedUserDao).updateTaskId(user.getId(), 3);
    }

    @Test
    public void nextTaskTest_noRemainingTasks() throws Exception {
        User user = new User(UUID.randomUUID(), ParticipantType.NOHINT);
        user.setCurrentTaskId(taskCount); // just finished last task

        boolean hasNext = taskHelper.getNextTask(user);
        assertFalse(hasNext);

        verify(mockedUserDao).updateTaskId(user.getId(), -1);
    }

    @Test
    public void nextTaskTest_alreadyFinished() throws Exception {
        User user = new User(UUID.randomUUID(), ParticipantType.NOHINT);
        user.setCurrentTaskId(-1); // experiment was already over

        boolean hasNext = taskHelper.getNextTask(user);
        assertFalse(hasNext);

        verify(mockedUserDao, never()).updateTaskId(user.getId(), -1);
    }

    // TODO: Add test for url in task response


    /**
     * Validate the text part of a task response
     */
    @Test
    public void validateTaskResponse_correct() {
        List<String> keywords = ImmutableList.of("keyword", "answer");
        when(mockedTaskDao.findKeywordsForTask(1)).thenReturn(keywords);

        TaskResponse taskResponse = new TaskResponse(1,  "The answer to this question is Keyword");
        assertTrue(taskHelper.validateTask(taskResponse));
    }

    @Test
    public void validateTaskResponse_incorrect() {
        List<String> keywords = ImmutableList.of("keyword", "answer");
        when(mockedTaskDao.findKeywordsForTask(1)).thenReturn(keywords);

        TaskResponse taskResponse = new TaskResponse(1,  "I don't understand the question");
        assertFalse(taskHelper.validateTask(taskResponse));
    }

    @Test
    public void validateTaskResponse_incomplete() {
        List<String> keywords = ImmutableList.of("keyword", "answer");
        when(mockedTaskDao.findKeywordsForTask(1)).thenReturn(keywords);

        TaskResponse taskResponse = new TaskResponse(1,  "Keyword");
        assertFalse(taskHelper.validateTask(taskResponse));
    }
}
