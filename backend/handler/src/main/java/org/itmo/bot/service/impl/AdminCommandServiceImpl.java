package org.itmo.bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.command.*;
import org.itmo.bot.exception.command.NoSuchCommandException;
import org.itmo.bot.service.CommandService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminCommandServiceImpl implements CommandService {

    private final ApplicationContext applicationContext;

    private static final Map<String, Class<? extends Command>> map = new HashMap<>();
    static {
        map.put("ПолучитьВсех", FindAllRegisteredStudentsCommand.class);
        map.put("НаписатьВсем", BroadcastCommand.class);
        map.put("Зарегистрировать", RegisterPersonCommand.class);
        map.put("Удалить", DeleteByIdCommand.class);
        map.put("РазделитьНаКоманды", SplitTeamsCommand.class);
    }

    @Override
    public void execute(String command, long chatId) {
        String[] input = command.split(" ", 2);
        Class<?> clazz = map.get(input[0]);
        if (clazz == null) {
            throw new NoSuchCommandException("Команда '%s' не найдена".formatted(input[0]));
        }
        Command c = (Command) applicationContext.getBean(clazz);
        c.setArgs(input);
        c.execute(chatId);
    }


}
