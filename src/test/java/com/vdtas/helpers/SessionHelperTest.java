package com.vdtas.helpers;

import com.vdtas.SessionException;
import com.vdtas.models.UserSession;
import com.vdtas.models.participants.ParticipantType;
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
        String id = UUID.randomUUID().toString();
        when(mockedSession.get("sessionId").isSet()).thenReturn(true);
        when(mockedSession.get("sessionId").value()).thenReturn(id);

        SessionHelper.findUserSession(mockedSession);
    }

    /**
     * Validate that an existing UserSession can be found.
     *
     * @throws Exception
     */
    @Test
    public void findUserSession() throws Exception {
        UUID id = UUID.randomUUID();
        UserSession expectedSession = new UserSession(id, ParticipantType.GENERIC_HINT);

        when(mockedSession.get("sessionId").isSet()).thenReturn(true);
        when(mockedSession.get("sessionId").value()).thenReturn(id.toString());

        sessionMap.put(id, expectedSession);

        assertEquals(expectedSession, SessionHelper.findUserSession(mockedSession));
    }

    @Test
    public void findOrCreateUserSession_noSessionInMap() throws Exception {
        UUID newId = UUID.randomUUID();
        when(mockedSession.get("sessionId").isSet()).thenReturn(false);
        when(mockedSession.get("sessionId").value()).thenReturn(newId.toString());

        UserSession userSession = findOrCreateUserSession(mockedSession);
        assertNotNull(userSession);
        assertTrue(SessionHelper.sessionMap.containsKey(userSession.getId()));
    }

    @Test
    public void findOrCreateUserSession_existingUserSession() throws Exception {
        UUID existingId = UUID.randomUUID();
        when(mockedSession.get("sessionId").isSet()).thenReturn(true);
        when(mockedSession.get("sessionId").value()).thenReturn(existingId.toString());

        UserSession userSession = new UserSession(existingId, ParticipantType.GENERIC_HINT);
        sessionMap.put(existingId, userSession);

        UserSession foundSession = findOrCreateUserSession(mockedSession);
        assertNotNull(foundSession);
        assertEquals(userSession, foundSession);
        assertEquals("There should only be one user session in the map", 1, sessionMap.size());
    }
}