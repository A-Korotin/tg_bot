package org.itmo.bot.state;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StateFactory {

    private final ApplicationContext context;

    public State getStateWithName(StateName name) {
        return context.getBean(name.getClazz());
    }
}
