package com.vdtas.models.hints;

import javax.inject.Singleton;

/**
 * @author vvandertas
 */
@Singleton
public class GenericHint extends Hint {
  private static final ParticipantType participantType = ParticipantType.GENERIC_HINT;

  public GenericHint() {
    super(participantType);
  }
}
