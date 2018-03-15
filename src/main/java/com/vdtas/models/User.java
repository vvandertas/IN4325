package com.vdtas.models;

import com.vdtas.models.participants.ParticipantType;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author vvandertas
 */
public class User {
    private UUID id;
    private ParticipantType participantType;
    private int currentTaskId = 1; // always start at task 1

    private Timestamp finishedAt;
    private Map<String, String> questionnaire; // json blob

    public User() {
    }

    public User(UUID id, ParticipantType participantType) {
        this.id = id;
        this.participantType = participantType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ParticipantType getParticipantType() {
        return participantType;
    }

    public void setParticipantType(ParticipantType participantType) {
        this.participantType = participantType;
    }

    public int getCurrentTaskId() {
        return currentTaskId;
    }

    public void setCurrentTaskId(int currentTaskId) {
        this.currentTaskId = currentTaskId;
    }

    public Timestamp getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Timestamp finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Map<String, String> getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Map<String, String>  questionnaire) {
        this.questionnaire = questionnaire;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", participantType=" + participantType +
                ", currentTaskId=" + currentTaskId +
                ", finishedAt=" + finishedAt +
                '}';
    }
}
