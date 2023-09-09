package org.itmo.bot.state;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class StateFactory {
    public static State getStatewithName(StateName name) {
        return stateMap.get(name);
    }

    private static final Map<StateName, State> stateMap = new HashMap<>();
    static {
        stateMap.put(StateName.START, new AnnotationConfigApplicationContext().getBean(StartState.class));
        stateMap.put(StateName.ORGANIZER, new AnnotationConfigApplicationContext().getBean(OrganizerState.class));
        stateMap.put(StateName.REGISTRATION_NAME, new AnnotationConfigApplicationContext().getBean(StateRegistrationName.class));
    }
}
