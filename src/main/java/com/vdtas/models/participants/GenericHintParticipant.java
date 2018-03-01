package com.vdtas.models.participants;

import javax.inject.Singleton;

/**
 * @author vvandertas
 */
@Singleton
public class GenericHintParticipant extends Participant {
    private static final ParticipantType participantType = ParticipantType.GENERIC_HINT;

    public GenericHintParticipant() {
        super(participantType);
    }
}
