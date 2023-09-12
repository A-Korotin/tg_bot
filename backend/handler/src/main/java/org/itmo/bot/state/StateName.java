package org.itmo.bot.state;

import lombok.Getter;

@Getter
public enum StateName {
    ORGANIZER(OrganizerState.class),
    START(StartState.class),
    REGISTRATION_NAME(StateRegistrationName.class),
    REGISTRATION_SURNAME(StateRegistrationSurname.class),
    REGISTRATION_ISU(StateRegistrationISU.class),
    REGISTRATION_GROUP(StateRegistrationGroup.class);

    StateName(Class<? extends State> stateClass) {
        this.clazz = stateClass;
    }

    private final Class<? extends State> clazz;
}
