package com.vdtas.helpers;

import com.vdtas.SessionException;
import com.vdtas.models.Task;
import com.vdtas.models.UserSession;
import com.vdtas.models.participants.ParticipantType;
import org.apache.commons.lang3.StringUtils;
import org.jooby.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static com.vdtas.helpers.SessionHelper.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * @author vvandertas
 */
public class SessionHelperTest {

    private Session mockedSession;

    @Before
    public void setup() {
        mockedSession = mock(Session.class, Mockito.RETURNS_DEEP_STUBS);
        sessionMap.clear();
    }

    /**
     * Test findUserSession without session id in the Jooby session
     *
     * @throws Exception
     */
    @Test(expected = SessionException.class)
    public void findUserSession_noSessionId() throws Exception {
        SessionHelper.findUserSession(mockedSession);
        when(mockedSession.get("sessionId").isSet()).thenReturn(false);
    }

    /**
     * Test findUserSession with session id from Jooby session, but without UserSession present.
     *
     * @throws Exception
     */
    @Test(expected = SessionException.class)
    public void findUserSession_missingUserSession() throws Exception {
        mockSessionId(true);

        SessionHelper.findUserSession(mockedSession);
    }

    /**
     * Validate that findOrCreateUserSession returns a new UserSession in case it is not yet present.
     *
     * @throws Exception
     */
    @Test
    public void findOrCreateUserSession_noSessionInMap() throws Exception {
        mockSessionId(false);

        UserSession userSession = findOrCreateUserSession(mockedSession);
        assertNotNull(userSession);
        assertTrue(SessionHelper.sessionMap.containsKey(userSession.getId()));
    }

    /**
     * Validate that an existing UserSession can be found
     *
     * @throws Exception
     */
    @Test
    public void findOrCreateUserSession_existingUserSession() throws Exception {
        UUID existingId = mockSessionId(true);

        UserSession userSession = new UserSession(existingId, ParticipantType.GENERIC_HINT);
        sessionMap.put(existingId, userSession);

        UserSession foundSession = findOrCreateUserSession(mockedSession);
        assertNotNull(foundSession);
        assertEquals(userSession, foundSession);
        assertEquals("There should only be one user session in the map", 1, sessionMap.size());
    }

    /**
     * Test hint retrieval without active task for user.
     * This should not cause an error but simple return an empty string
     *
     * @throws Exception
     */
    @Test
    public void findHint_noCurrentTask() throws Exception {
        UUID id = mockSessionId(true);

        // make sure a user session exists
        UserSession userSession = new UserSession(id, ParticipantType.GENERIC_HINT);
        sessionMap.put(id, userSession);

        String currentHint = SessionHelper.findCurrentHint(mockedSession);
        assertTrue(StringUtils.isEmpty(currentHint));
    }

    /**
     * Test hint retrieval for all participant types.
     *
     * @throws Exception
     */
    @Test
    public void findHint_withCurrentTask() throws Exception {
        // Create a task for the userSession
        Task task = new Task(1, "testName", "This is a test question");
        String specificHint = "Specific Hint";
        task.setSpecificHint(specificHint);

        // make sure a user session exists
        UUID id = mockSessionId(true);

        UserSession userSession = new UserSession(id, ParticipantType.GENERIC_HINT);
        userSession.setCurrentTask(task);
        sessionMap.put(id, userSession);

        // User is of type generic hint
        String hint = SessionHelper.findCurrentHint(mockedSession);
        assertEquals("Expecting generic hint", Task.GENERIC_HINT, hint);

        // Update user to specific hint
        userSession = new UserSession(id, ParticipantType.SPECIFIC_HINT);
        userSession.setCurrentTask(task);
        sessionMap.put(id, userSession);

        hint = SessionHelper.findCurrentHint(mockedSession);
        assertEquals("Expecting specific hint", specificHint, hint);

        // Update user to no hint
        userSession = new UserSession(id, ParticipantType.NO_HINT);
        userSession.setCurrentTask(task);
        sessionMap.put(id, userSession);

        hint = SessionHelper.findCurrentHint(mockedSession);
        assertEquals("Expecting no hint", "", hint);
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