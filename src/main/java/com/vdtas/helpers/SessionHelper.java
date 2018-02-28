package com.vdtas.helpers;

import com.vdtas.models.UserSession;
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

  public static void validateUserSession(Session session) {
    // get user session id
    UUID sessionId = checkSessionId(session);
    if(!sessionMap.containsKey(sessionId)) {
      logger.info("Session does not exist yet, creating one.");
      UserSession userSession = new UserSession(sessionId);
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
    if(!session.get(SESSION_ID).isSet()) {
      logger.info("Session id not found. Creating one now");
      session.set(SESSION_ID, UUID.randomUUID().toString());
    } else {
      logger.info("Existing session id found");
    }

    return UUID.fromString(session.get(SESSION_ID).value());
  }

  /**
   * Increment the global query count by 1 for this session
   * @param session
   */
  public static void incrementQueryCount(Session session) {
    if(!session.get(SESSION_ID).isSet()) {
      logger.error("Session not found when performing search");
      // TODO: Throw error or something
    }

    logger.info("Incrementing user session query count");
    String sessionId = session.get(SESSION_ID).value();
    UserSession userSession = sessionMap.get(UUID.fromString(sessionId));

    logger.info("New count: " + userSession.incrementCount());

    sessionMap.put(userSession.getId(),userSession);
  }
}
