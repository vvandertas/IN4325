package com.vdtas.helpers;

import com.google.common.collect.ImmutableList;
import com.vdtas.models.hints.*;

import javax.inject.Singleton;


/**
 * @author vvandertas
 */
@Singleton
public class ExperimentHelper {
    private static NoHint noHint = new NoHint();
    private static GenericHint genericHint = new GenericHint();
    private static SpecificHint specificHint = new SpecificHint();

    public static synchronized ParticipantType selectParticipantType() {
        Hint selectedHint = Hint.randomMin(ImmutableList.of(noHint, genericHint, specificHint)).increment();
        return selectedHint.getParticipantType();
    }


}
