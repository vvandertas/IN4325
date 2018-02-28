package com.vdtas.models.hints;

/**
 * @author vvandertas
 */
public class SpecificHint extends Hint{
  private static final ParticipantType participantType = ParticipantType.SPECIFIC_HINT;

  public SpecificHint() {
    super(participantType);
  }
}
