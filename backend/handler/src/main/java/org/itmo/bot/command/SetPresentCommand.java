package org.itmo.bot.command;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.exception.command.InvalidArgumentsException;
import org.itmo.bot.service.StudentService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetPresentCommand extends Command {

    private final StudentService studentService;

    @Override
    public void execute(long chatId) {
        if (args.length != 2) {
            throw new InvalidArgumentsException("Ожидался аргумент через пробел - ID регистрации");
        }
        long id;
        try {
            id = Long.parseLong(args[1]);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException("Ожидался ID регистрации, получено '%s'".formatted(args[1]));
        }

        studentService.setPresent(id);
    }
}
