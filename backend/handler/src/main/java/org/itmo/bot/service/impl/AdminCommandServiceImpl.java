package org.itmo.bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.command.Command;
import org.itmo.bot.command.FindAllRegisteredStudentsCommand;
import org.itmo.bot.exception.command.NoSuchCommandException;
import org.itmo.bot.service.AdminCommandService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminCommandServiceImpl implements AdminCommandService {

    private final ApplicationContext applicationContext;

    private static final Map<String, Class<? extends Command>> map = new HashMap<>();
    static {
        map.put("Получить всех", FindAllRegisteredStudentsCommand.class);
    }

    @Override
    public void execute(String command, long chatId) {
        Class<?> clazz = map.get(command);
        if (clazz == null) {
            throw new NoSuchCommandException(command);
        }
        Command c = (Command) applicationContext.getBean(clazz);
        c.execute(chatId);
    }


}
