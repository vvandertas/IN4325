package com.vdtas.helpers;

import com.vdtas.daos.TaskDao;
import com.vdtas.daos.UserDao;
import com.vdtas.models.Task;
import com.vdtas.models.User;
import com.vdtas.models.participants.ParticipantType;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static com.vdtas.helpers.TaskHelper.maxTaskId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

/**
 * @author vvandertas
 */
public class TaskHelperTest {


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
        User user = new User(UUID.randomUUID(), ParticipantType.NO_HINT);
        user.setCurrentTaskId(2);

        Task expectedTask = new Task(3, "Nice Question!", "with a name");
        when(mockedTaskDao.findById(user.getCurrentTaskId() + 1)).thenReturn(expectedTask);

        Task nextTask = taskHelper.getNextTask(user);
        assertNotNull(nextTask);
        assertEquals(expectedTask, nextTask);

        verify(mockedUserDao).updateTaskId(user.getId(), 3);
        verify(mockedTaskDao).findById(3);
    }

    @Test
    public void nextTaskTest_noRemainingTasks() throws Exception {
        User user = new User(UUID.randomUUID(), ParticipantType.NO_HINT);
        user.setCurrentTaskId(maxTaskId); // just finished last task

        Task nextTask = taskHelper.getNextTask(user);
        assertNull(nextTask);

        verify(mockedUserDao).updateTaskId(user.getId(), -1);
        verify(mockedTaskDao, never()).findById(anyInt());
    }

    @Test
    public void nextTaskTest_alreadyFinished() throws Exception {
        User user = new User(UUID.randomUUID(), ParticipantType.NO_HINT);
        user.setCurrentTaskId(-1); // experiment was already over

        Task nextTask = taskHelper.getNextTask(user);
        assertNull(nextTask);

        verify(mockedUserDao, never()).updateTaskId(user.getId(), -1);
        verify(mockedTaskDao, never()).findById(anyInt());
    }
}
