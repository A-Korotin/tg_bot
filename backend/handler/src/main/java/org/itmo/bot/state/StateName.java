package org.itmo.bot.state;

import lombok.Getter;

@Getter
public enum StateName {
    ORGANIZER(OrganizerState.class),
    START(StartState.class),
    REGISTRATION_NAME(NameState.class),
    REGISTRATION_SURNAME(null); // todo set actual classes

    StateName(Class<? extends State> stateClass) {
        this.clazz = stateClass;
    }

    private final Class<? extends State> clazz;
}
