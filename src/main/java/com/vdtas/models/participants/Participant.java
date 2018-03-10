package com.vdtas.models.participants;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author vvandertas
 */
public class Participant {
    private final ParticipantType participantType;
    private int count;

    public Participant(ParticipantType participantType) {
        this.participantType = participantType;
        count = 0;
    }

    public ParticipantType getParticipantType() {
        return participantType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Participant increment() {
        count++;
        return this;
    }

    /**
     * Return the Hint with the lowest count
     */
    public static Participant min(List<Participant> participants) {
        Participant minParticipant = participants.get(0);
        for (int i = 1; i < participants.size(); i++) {
            if (participants.get(i).count < minParticipant.count) {
                minParticipant = participants.get(i);
            }
        }

        return minParticipant;
    }

    /**
     * Return Hint with lowest count. In case there are multiple hints with the same lowest count,
     * select a random hint from those.
     */
    public static Participant randomMin(List<Participant> participants) {
        List<Participant> minParticipants = new ArrayList<>();
        int minCount = Integer.MAX_VALUE;

        for (Participant h : participants) {
            if (h.count < minCount) {
                minCount = h.count;
                minParticipants.clear();
                minParticipants.add(h);
            } else if (h.count == minCount) {
                minParticipants.add(h);
            }
        }
        if (minParticipants.size() > 1) {
            return minParticipants.get(ThreadLocalRandom.current().nextInt(0, minParticipants.size()));
        } else
            return minParticipants.get(0);
    }

    @Override
    public String toString() {
        return "Participant{" +
                "participantType=" + participantType +
                ", count=" + count +
                '}';
    }
}
