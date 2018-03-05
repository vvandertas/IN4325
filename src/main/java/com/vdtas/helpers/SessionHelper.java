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
    protected static HashMap<UUID, UserSession> sessionMap = new java.util.HashMap<>();
    private static Logger logger = LoggerFactory.getLogger(SessionHelper.class);

    /**
     * Get the users sessionId and find or create its UserSession.
     *
     * @param session
     */
    public static UserSession findOrCreateUserSession(Session session) {
        // get user session id
        UUID sessionId = findOrCreateSessionId(session);

        // Create a UserSession if it doesn't exist yet.
        if (!sessionMap.containsKey(sessionId)) {
            logger.info("Session does not exist yet, creating one.");

            // Create new user session and assign a participant type.
            ParticipantType participantType = ExperimentHelper.selectParticipantType();
            UserSession userSession = new UserSession(sessionId, participantType);

            // Select first task for user
            userSession.setCurrentTask(userSession.getRemainingTasks().next());

            sessionMap.put(sessionId, userSession);
        }

        return sessionMap.get(sessionId);
    }

    /**
     * Check if we already have a session id for the user. If not create one.
     *
     * @param session
     * @return
     */
    private static UUID findOrCreateSessionId(Session session) {
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

        int queryCount = userSession.incrementQueryCount();
        logger.info("Incremented query count for : " + queryCount);
        sessionMap.put(userSession.getId(), userSession);
    }

    /**
     * Find a user session from a Jooby session
     * @param session
     * @return
     * @throws SessionException
     */
    protected static UserSession findUserSession(Session session) throws SessionException{
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

    /**
     * Based on the remaining tasks for a user session, randomly select the next task
     * and update the current task for this user session.
     *
     * The task returned is null if the user has completed all tasks in the experiment
     *
     * @param session
     * @return
     * @throws SessionException
     */
    public static Task updateUserTask(Session session) throws SessionException {
        UserSession userSession = findUserSession(session);

        Tasks remainingTasks = userSession.getRemainingTasks();
        Task task = remainingTasks.next();
        userSession.setCurrentTask(task);

        sessionMap.put(userSession.getId(), userSession);

        return task;
    }

    /**
     * Retrieve the correct hint from the current task for a user session.
     *
     * @param session
     * @return
     * @throws SessionException
     */
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
