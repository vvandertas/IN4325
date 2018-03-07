package com.vdtas.helpers;

import com.google.common.collect.ImmutableList;
import com.vdtas.daos.HintDao;
import com.vdtas.daos.UserTaskDataDao;
import com.vdtas.models.*;
import com.vdtas.models.participants.ParticipantType;
import org.apache.commons.lang3.StringUtils;
import org.jooby.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author vvandertas
 */
public class ExperimentHelperTest {

    private Session mockedSession;
    private HintDao mockedHintDao;
    private UserTaskDataDao mockedDataDao;
    private ExperimentHelper experimentHelper;

    @Before
    public void setup() {
        mockedHintDao = mock(HintDao.class);
        mockedDataDao = mock(UserTaskDataDao.class);
        experimentHelper = new ExperimentHelper(mockedDataDao, mockedHintDao);
        mockedSession = mock(Session.class, Mockito.RETURNS_DEEP_STUBS);
    }

//    /**
//     * Test hint retrieval without active task for user.
//     * This should not cause an error but simple return an empty string
//     *
//     * @throws Exception
//     */
//    @Test
//    public void findHint_noCurrentTask() throws Exception {
//        UUID id = mockSessionId(true);
//
//        // make sure a user session exists
//        User user = new User();
//        user.setParticipantType(ParticipantType.GENERIC_HINT);
//        user.setId(id);
//
//        when(experimentHelper.hintDao.findByTaskId(-1)).thenReturn(user);
//
//        String currentHint = SessionHelper.findCurrentHint(mockedSession);
//        assertTrue(StringUtils.isEmpty(currentHint));
//    }
    /**
     * Test hint retrieval for all participant types.
     *
     * @throws Exception
     */
    @Test
    public void findHint_withCurrentTask() throws Exception {
        // Create a task for the userSession
        Task task = new Task(1, "testName", "This is a test question");

        Hint hint1 = new Hint(1, task.getId(), "This is specific hint 1 for task 1");
        Hint hint2 = new Hint(2, task.getId(), "This is specific hint 2 for task 1");

        when(mockedHintDao.findByTaskId(1)).thenReturn(ImmutableList.of(hint1, hint2));

        // make sure a user session exists
        UUID id = mockSessionId(true);

        User user = new User(id, ParticipantType.SPECIFIC_HINT);
        user.setCurrentTaskId(task.getId());

        // User is of type specific hint
        List<Hint> hints = experimentHelper.findCurrentHints(user, task);
        assertNotNull(hints);
        assertEquals("expecting 2 specific hints", 2, hints.size());

        // now finding the generic hint
        user.setParticipantType(ParticipantType.GENERIC_HINT);
        hints = experimentHelper.findCurrentHints(user, task);
        assertEquals(1, hints.size());
        assertEquals(Task.GENERIC_HINT, hints.get(0).getHint());

        // Update user to no hint
        user.setParticipantType(ParticipantType.NO_HINT);
        hints = experimentHelper.findCurrentHints(user, task);
        assertNotNull(hints);
        assertTrue(hints.isEmpty());
    }



    /**
     * Helper to mock the retrieval of the sessionId from a Session.
     *
     * @param isSet indicates if we want to let isSet return true or false
     * @return
     */
    private UUID mockSessionId(boolean isSet) {
        UUID id = UUID.randomUUID();
        when(mockedSession.get("sessionId").isSet()).thenReturn(isSet);
        when(mockedSession.get("sessionId").value()).thenReturn(id.toString());

        return id;
    }
}
