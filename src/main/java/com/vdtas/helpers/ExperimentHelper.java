package com.vdtas.helpers;

import com.google.common.collect.ImmutableList;
import com.vdtas.models.participants.*;

import javax.inject.Singleton;


/**
 * @author vvandertas
 */
@Singleton
public class ExperimentHelper {
    private static NoHintParticipant noHint = new NoHintParticipant();
    private static GenericHintParticipant genericHint = new GenericHintParticipant();
    private static SpecificHintParticipant specificHint = new SpecificHintParticipant();

    public static synchronized ParticipantType selectParticipantType() {
        Participant selectedParticipant = Participant.randomMin(ImmutableList.of(noHint, genericHint, specificHint)).increment();
        return selectedParticipant.getParticipantType();
    }


}
