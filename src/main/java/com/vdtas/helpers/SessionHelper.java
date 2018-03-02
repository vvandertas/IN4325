package com.vdtas.helpers;

import com.vdtas.SessionException;
import com.vdtas.models.Task;
import com.vdtas.models.Tasks;
import com.vdtas.models.UserSession;
import com.vdtas.models.participants.ParticipantType;
import org.jooby.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author vvandertas
 */
@Singleton
public class SessionHelper {
    private static final String SESSION_ID = "sessionId";
    private static HashMap<UUID, UserSession> sessionMap = new java.util.HashMap<>();
    private static Logger logger = LoggerFactory.getLogger(SessionHelper.class);

    public static void findOrCreateUserSession(Session session) {
        // get user session id
        UUID sessionId = checkSessionId(session);

        // Create a UserSession if it doesn't exist yet.
        if (!sessionMap.containsKey(sessionId)) {
            logger.info("Session does not exist yet, creating one.");

            // Create new user session and assign a participant type.
            ParticipantType participantType = ExperimentHelper.selectParticipantType();
            UserSession userSession = new UserSession(sessionId, participantType);

            // Select first task for user
            userSession.setCurrentTask(userSession.getRemainingTasks().randomNext());

            sessionMap.put(sessionId, userSession);
        }
    }

    /**
     * Check if we already have a session id for the user. If not create one.
     *
     * @param session
     * @return
     */
    private static UUID checkSessionId(Session session) {
        if (!session.get(SESSION_ID).isSet()) {
            logger.info("Session id not found. Creating one now");
            session.set(SESSION_ID, UUID.randomUUID().toString());
        } else {
            logger.info("Existing session id found");
        }

        return UUID.fromString(session.get(SESSION_ID).value());
    }

    /**
     * Increment the global query count by 1 for this session
     *
     * @param session
     */
    public static void incrementQueryCount(Session session) throws SessionException {
        UserSession userSession = findUserSession(session);

        userSession.incrementCount();
        sessionMap.put(userSession.getId(), userSession);
    }

    public static ParticipantType findParticipantType(Session session) throws SessionException {
        UserSession userSession = findUserSession(session);
        return userSession.getParticipantType();
    }

    public static Task findCurrentTask(Session session) throws SessionException {
        UserSession userSession = findUserSession(session);
        return userSession.getCurrentTask();
    }

    public static UserSession findUserSession(Session session) throws SessionException{
        if (!session.get(SESSION_ID).isSet()) {
            throw new SessionException("Missing session id");
        }

        String sessionId = session.get(SESSION_ID).value();
        UserSession userSession = sessionMap.get(UUID.fromString(sessionId));
        if(userSession != null) {
            return userSession;
        } else {
            throw new SessionException("UserSession not found.");
        }
    }

    public static Task updateUserTask(Session session) throws SessionException {
        UserSession userSession = findUserSession(session);

        Tasks remainingTasks = userSession.getRemainingTasks();
        Task task = remainingTasks.randomNext();
        userSession.setCurrentTask(task);

        sessionMap.put(userSession.getId(), userSession);

        return task;
    }

    public static String findCurrentHint(Session session) throws SessionException {
        UserSession userSession = findUserSession(session);

        Task task = userSession.getCurrentTask();
        ParticipantType participantType = userSession.getParticipantType();

        String hint = "";
        if(task != null) {
            // Find the correct hint to show
            switch (participantType) {
                case GENERIC_HINT:
                    hint = task.getGenericHint();
                    break;
                case SPECIFIC_HINT:
                    hint = task.getSpecificHint();
                    break;
                default:
                    break;
            }
        }

        return hint;
    }
}
