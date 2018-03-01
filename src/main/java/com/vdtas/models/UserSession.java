package com.vdtas.models;

import com.vdtas.models.participants.ParticipantType;

import java.util.UUID;

/**
 * @author vvandertas
 */
public class UserSession {

    private UUID id;
    private int queryCount = 0;
    private ParticipantType participantType;

    public UserSession(UUID id, ParticipantType pt) {
        this.id = id;
        participantType = pt;
    }

    public UUID getId() {
        return id;
    }

    public int getQueryCount() {
        return queryCount;
    }

    public ParticipantType getParticipantType() {
        return participantType;
    }

    /**
     * Increment and return the query count
     * @return
     */
    public int incrementCount() {
        return ++queryCount;
    }
}
