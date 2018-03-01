package com.vdtas.models.participants;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author vvandertas
 */
public class ParticipantTest {
    private List<Participant> participants;
    private GenericHintParticipant genericHint;
    private NoHintParticipant noHint;
    private SpecificHintParticipant specificHint;

    @Before
    public void setup() {
        participants = new ArrayList<>();
        // Create three hint instances
        genericHint = new GenericHintParticipant();
        noHint = new NoHintParticipant();
        specificHint = new SpecificHintParticipant();

        // and add them to the hints list
        participants.add(genericHint);
        participants.add(noHint);
        participants.add(specificHint);
    }

    @Test
    public void minTest() throws Exception{
        // Make sure noHint has lowest count
        genericHint.increment();
        specificHint.increment();

        Participant minParticipant = Participant.min(participants);
        assertEquals("Expecting noHint", noHint, minParticipant);

        minParticipant = Participant.randomMin(participants);
        assertEquals("Still expecting noHint", noHint, minParticipant);

        // All counts are equal
        noHint.increment();
        minParticipant = Participant.min(participants);
        assertEquals("Expecting genericHint", genericHint, minParticipant);

        // Expecting noHint or hint3
        genericHint.increment();
        minParticipant = Participant.min(participants);
        assertNotEquals("This should not be genericHint", genericHint, minParticipant);
    }

    @Test
    public void minRandomTest() throws Exception{
        // Make sure noHint has lowest count
        genericHint.increment();
        specificHint.increment();

        Participant minParticipant = Participant.randomMin(participants);
        assertEquals("Expecting noHint", noHint, minParticipant);

        // noHint and specificHint now have lowest count
        noHint.increment();
        genericHint.increment();

        // expecting the result to be anything but genericHint
        minParticipant = Participant.min(participants);
        assertNotEquals("This should not be genericHint", genericHint, minParticipant);
    }
}
