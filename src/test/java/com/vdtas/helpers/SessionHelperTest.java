package com.vdtas.helpers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.vdtas.daos.UserDao;
import com.vdtas.models.User;
import com.vdtas.models.participants.ParticipantType;
import org.jdbi.v3.core.Jdbi;
import org.jooby.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.vdtas.helpers.SessionHelper.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


/**
 * @author vvandertas
 */
public class SessionHelperTest {

    private Session mockedSession;
    private SessionHelper sessionHelper;
    private UserDao mockedUserDao;
    private Jdbi mockedJdbi;

    @Before
    public void setup() {
        mockedUserDao = mock(UserDao.class);
        mockedSession = mock(Session.class, Mockito.RETURNS_DEEP_STUBS);
        mockedJdbi = mock(Jdbi.class);

        sessionHelper = new SessionHelper("unitTest", mockedUserDao);
    }

    /**
     * Test findUserSession without session id in the Jooby session
     *
     * @throws Exception
     */
    @Test
    public void findOrCreateUserId_newUserId() throws Exception {

        UUID uuid = mockUserSession(false);
        UUID userId = sessionHelper.findOrCreateUserId(mockedSession, Optional.empty());
        verify(mockedSession).set(eq(USER_ID), any());

        assertNotNull(userId);
        assertEquals(uuid, userId);
    }

    /**
     * Test findUserSession without session id in the Jooby session
     *
     * @throws Exception
     */
    @Test
    public void findOrCreateUserId_existingUserId() throws Exception {

        UUID uuid = mockUserSession(true);
        UUID userId = sessionHelper.findOrCreateUserId(mockedSession, Optional.empty());
        verify(mockedSession, never()).set(eq(USER_ID), any());

        assertNotNull(userId);
        assertEquals(uuid, userId);
    }

    /**
     * Validate that findOrCreateUserSession returns a new UserSession in case it is not yet present.
     *
     * @throws Exception
     */
    @Test
    public void findOrCreateUser_newUser() throws Exception {
        UUID userId = mockUserSession(false);
        when(mockedUserDao.insert(eq(userId), any())).thenReturn(new User(userId, ParticipantType.NOHINT));

        boolean createdUser = sessionHelper.findOrCreateUser(mockedSession, Optional.empty());
        assertTrue(createdUser);
        verify(mockedUserDao).findById(userId);
        verify(mockedUserDao).insert(eq(userId), any());
    }

    /**
     * Validate that an existing UserSession can be found
     *
     * @throws Exception
     */
    @Test
    public void findOrCreateUser_existingUser() throws Exception {
        UUID existingId = mockUserSession(true);

        User user = new User();
        user.setId(existingId);
        user.setParticipantType(ParticipantType.GENERICHINT);

        when(mockedUserDao.findById(existingId)).thenReturn(user);

        boolean createdUser = sessionHelper.findOrCreateUser(mockedSession, Optional.empty());
        assertFalse(createdUser);
        verify(mockedUserDao).findById(existingId);
        verify(mockedUserDao, never()).insert(eq(existingId), any());

    }


    @Test
    public void questionnaireTest() throws Exception {
        UUID existingId = mockUserSession(true);

        User user = new User();
        user.setId(existingId);
        user.setParticipantType(ParticipantType.GENERICHINT);

        Map<String, String> answers = ImmutableMap.of("Q1", "Answer 1","Q2", "Answer 2");
        sessionHelper.saveQuestionnaireData(user, answers);
        sessionHelper.findOrCreateUser(mockedSession, Optional.empty());


    }


    /**
     * Helper to mock the retrieval of the sessionId from a Session.
     *
     * @param isSet indicates if we want to let isSet return true or false
     * @return
     */
    private UUID mockUserSession(boolean isSet) {
        UUID id = UUID.randomUUID();
        when(mockedSession.get(USER_ID).isSet()).thenReturn(isSet);
        when(mockedSession.get(USER_ID).value()).thenReturn(id.toString());

        return id;
    }
}