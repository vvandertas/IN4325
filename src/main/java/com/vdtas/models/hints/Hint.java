package com.vdtas.models.hints;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author vvandertas
 */
public abstract class Hint {
    private final ParticipantType participantType;
    private int count;

    protected Hint(ParticipantType participantType) {
        this.participantType = participantType;
        count = 0;
    }

    public ParticipantType getParticipantType() {
        return participantType;
    }

    public int getCount() {
        return count;
    }

    public Hint increment() {
        count++;
        return this;
    }

    /**
     * Return the Hint with the lowest count
     */
    public static Hint min(List<Hint> hints) {
        Hint minHint = hints.get(0);
        for (int i = 1; i < hints.size(); i++) {
            if(hints.get(i).count < minHint.count) {
                minHint = hints.get(i);
            }
        }

        return minHint;
    }

    /**
     * Return Hint with lowest count. In case there are multiple hints with the same lowest count,
     * select a random hint from those.
     */
    public static Hint randomMin(List<Hint> hints) {
        List<Hint> minHints = new ArrayList<>();
        int minCount = Integer.MAX_VALUE;

        for (Hint h : hints) {
            if (h.count < minCount) {
                minCount = h.count;
                minHints.clear();
                minHints.add(h);
            } else if (h.count == minCount) {
                minHints.add(h);
            }
        }
        if (minHints.size() > 1) {
            return minHints.get(ThreadLocalRandom.current().nextInt(0, minHints.size()));
        } else
            return minHints.get(0);
    }

}
