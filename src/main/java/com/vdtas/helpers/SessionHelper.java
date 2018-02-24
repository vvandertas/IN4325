package com.vdtas.helpers;

import com.vdtas.models.UserSession;
import org.jooby.Session;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author vvandertas
 */
@Singleton
public class SessionHelper {
  private static HashMap<UUID, UserSession> sessionMap = new java.util.HashMap<>();

  public static void validateUserSession(Session session) {
    // get user session id
    UUID sessionId = checkSessionId(session);
    if(!sessionMap.containsKey(sessionId)) {
      System.out.println("Session does not exist yet, creating one.");
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
    if(!session.get("sessionId").isSet()) {
      System.out.println("Session id not found. Creating one now");
      session.set("sessionId", UUID.randomUUID().toString());
    } else {
      System.out.println("Existing session id found");
    }

    return UUID.fromString(session.get("sessionId").value());
  }

  public static void incrementQueryCount(Session session) {
    if(!session.get("sessionId").isSet()) {
      System.out.println("ERROR: Session id not found!!");
      // TODO: Throw error or something
    }

    System.out.println("Incrementing user session query count");
    String sessionId = session.get("sessionId").value();
    UserSession userSession = sessionMap.get(UUID.fromString(sessionId));

    System.out.println("New count: " + userSession.incrementCount());

    sessionMap.put(userSession.getId(),userSession);
  }
}
