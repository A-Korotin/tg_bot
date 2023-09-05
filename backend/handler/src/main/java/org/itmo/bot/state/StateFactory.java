package org.itmo.bot.state;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class StateFactory {
    public static State getStatewithName(StateName name) {
        return stateMap.get(name).get();
    }

    private static final Map<StateName, Supplier<State>> stateMap = new HashMap<>();
    static {
        stateMap.put(StateName.START, StartState::new);
        stateMap.put(StateName.ORGANIZER, OrganizerState::new);
    }
}
