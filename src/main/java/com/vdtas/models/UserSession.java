package com.vdtas.models;

import java.util.UUID;

/**
 * @author vvandertas
 */
public class UserSession {

  UUID id;
  int queryCount = 0;

  public UserSession(UUID id) {
    this.id = id;
  }

  public int incrementCount(){
    return ++queryCount;
  }

  public UUID getId() {
    return id;
  }

  public int getQueryCount() {
    return queryCount;
  }
}
