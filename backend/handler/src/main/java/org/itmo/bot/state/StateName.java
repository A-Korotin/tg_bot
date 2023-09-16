package org.itmo.bot.state;

import lombok.Getter;
import org.itmo.bot.state.afterparty.*;
import org.itmo.bot.state.registration.*;

@Getter
public enum StateName {
    ORGANIZER(OrganizerState.class),
    START(StartState.class),
    REGISTRATION_NAME(StateRegistrationName.class),
    REGISTRATION_SURNAME(StateRegistrationSurname.class),
    REGISTRATION_ISU(StateRegistrationISU.class),
    REGISTRATION_GROUP(StateRegistrationGroup.class),
    REGISTRATION_CONFIRMATION(StateRegistrationConfirmation.class),
    AFTER_PARTY_REGISTRATION_QUESTION(StateAfterPartyQuestion.class),
    AFTER_PARTY_REGISTRATION_PHOTO_OF_PAID(StateAfterPartyPhotoOfPaid.class),
    AFTER_PARTY_REGISTRATION_PHONE(StateAfterPartyPhone.class);

    StateName(Class<? extends State> stateClass) {
        this.clazz = stateClass;
    }

    private final Class<? extends State> clazz;
}
