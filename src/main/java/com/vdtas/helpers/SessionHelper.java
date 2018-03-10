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
    protected static final String USER_ID = "userId";
    private final String env;
    protected final UserDao userDao;

    @Inject
    public SessionHelper(@Named("application.env") String env, UserDao userDao) {
        this.env = env;
        this.userDao = userDao;
    }

    public User findOrCreateUser(Session session, Optional<String> forceType) {
        UUID userId = findOrCreateUserId(session);

        User user = userDao.findById(userId);
        if (user == null) {
            logger.info("User does not exist yet, creating one.");

            // Create new user and assign a participant type.
            ParticipantType participantType = selectParticipantType(forceType);
            user = userDao.insert(userId, participantType.toString(), 0);
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


    public synchronized ParticipantType selectParticipantType(Optional<String> forceType) {
        if("test".equals(env) && forceType.isPresent()) {
            return ParticipantType.valueOf(forceType.get());
        }

        Map<String, Integer> counts = userDao.getParticipantCounts();
        logger.debug("COUNTS: " + counts);

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

}
