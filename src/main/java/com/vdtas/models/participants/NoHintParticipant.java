package com.vdtas.models.participants;

import javax.inject.Singleton;

/**
 * @author vvandertas
 */
@Singleton
public class NoHintParticipant extends Participant {
    private static final ParticipantType participantType = ParticipantType.GENERIC_HINT;

    public NoHintParticipant() {
        super(participantType);
    }
}
