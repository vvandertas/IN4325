package com.vdtas.models.participants;

/**
 * @author vvandertas
 */
public class SpecificHintParticipant extends Participant {
    private static final ParticipantType participantType = ParticipantType.SPECIFIC_HINT;

    public SpecificHintParticipant() {
        super(participantType);
    }
}
