package com.vdtas.helpers;

import com.vdtas.daos.UserDao;
import com.vdtas.models.User;
import com.vdtas.models.participants.ParticipantType;
import org.jooby.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.UUID;

/**
 * @author vvandertas
 */
public class SessionHelper {
    private static final Logger logger = LoggerFactory.getLogger(SessionHelper.class);
    protected static final String USER_ID = "userId";
    protected final UserDao userDao;

    @Inject
    public SessionHelper(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findOrCreateUser(Session session) {
        UUID userId = findOrCreateUserId(session);

        User user = userDao.findById(userId);
        if (user == null) {
            logger.info("User does not exist yet, creating one.");

            // Create new user and assign a participant type.
            ParticipantType participantType = ExperimentHelper.selectParticipantType();
            user = userDao.insert(userId, participantType.toString());
        }

        return user;
    }

    /**
     * Check if we already have a session id for the user. If not create one.
     *
     * @param session
     * @return
     */
    protected UUID findOrCreateUserId(Session session) {
        if (!session.get(USER_ID).isSet()) {
            logger.info("Session id not found. Creating one now");
            session.set(USER_ID, UUID.randomUUID().toString());
        } else {
            logger.info("Existing session id found");
        }

        return UUID.fromString(session.get(USER_ID).value());
    }
}
