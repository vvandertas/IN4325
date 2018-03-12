package com.vdtas.helpers;

import com.vdtas.daos.UserDao;
import com.vdtas.models.User;
import com.vdtas.models.participants.Participant;
import com.vdtas.models.participants.ParticipantType;
import org.jooby.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

/**
 * @author vvandertas
 */
public class SessionHelper {
    private static final Logger logger = LoggerFactory.getLogger(SessionHelper.class);
    public static final String USER_ID = "userId";

    private final String env;
    private final UserDao userDao;

    @Inject
    public SessionHelper(@Named("application.env") String env, UserDao userDao) {
        this.env = env;
        this.userDao = userDao;
    }

    /**
     * Find or create a user using the userId from the session
     *
     * @param session Jooby session
     * @param forceType optional get parameter used for testing
     *
     * @return boolean indicating whether or not a new user was created
     */
    public boolean findOrCreateUser(Session session, Optional<String> forceType) {
        UUID userId = findOrCreateUserId(session, forceType);

        boolean createdNewUser = false;
        User user = userDao.findById(userId);
        if (user == null) {
            logger.info("User does not exist yet, creating one.");

            createdNewUser = true;
            // Create new user and assign a participant type.
            ParticipantType participantType = selectParticipantType(forceType);
            userDao.insert(userId, participantType.toString());
        }

        return createdNewUser;
    }

    /**
     * Check if we already have a session id for the user. If not create one.
     *
     * @param session current Jooby session
     * @return a user id
     */
    protected UUID findOrCreateUserId(Session session, Optional<String> forceType) {
        if (!session.get(USER_ID).isSet() || ("test".equals(env) && forceType.isPresent())) {
            logger.info("Session id not found. Creating one now");
            session.set(USER_ID, UUID.randomUUID().toString());
        } else {
            logger.info("Existing session id found");
        }

        return UUID.fromString(session.get(USER_ID).value());
    }


    /**
     * Select the next participant type based on the current counts per type.
     * If there are multiple types with the lowest count one is selected randomly from those.
     *
     * @param forceType testing parameter
     * @return the selected participant type
     */
    public synchronized ParticipantType selectParticipantType(Optional<String> forceType) {
        if("test".equals(env) && forceType.isPresent()) {
            return ParticipantType.valueOf(forceType.get());
        }

        Map<String, Integer> counts = userDao.getParticipantCounts();

        List<Participant> participants = new ArrayList<>();
        ParticipantType[] types = ParticipantType.values();
        for (int i = 0; i < types.length; i++) {
            ParticipantType participantType = types[i];
            Participant p = new Participant(participantType);

            if(counts.containsKey(participantType.toString())) {
                p.setCount(counts.get(participantType.toString()));
            }
            participants.add(p);
        }
        
        Participant selectedParticipant = Participant.randomMin(participants);
        logger.debug("Selected participant type: " + selectedParticipant.toString());

        return selectedParticipant.getParticipantType();
    }

    /**
     * Register the end of the experiment for a user
     *
     * @param user current user
     */
    public void registerEndOfExperiment(User user) {
        if(user.getFinishedAt() == null) {
            userDao.setFinished(user.getId());
        }
    }

}
