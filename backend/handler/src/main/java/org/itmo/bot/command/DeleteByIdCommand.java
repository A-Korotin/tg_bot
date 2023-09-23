package org.itmo.bot.command;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.exception.command.InvalidArgumentsException;
import org.itmo.bot.exception.command.StudentNotFoundException;
import org.itmo.bot.service.StudentService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteByIdCommand extends Command {
    private final StudentService studentService;

    @Override
    public void execute(long chatId) {
        if (args.length != 2) {
            throw new
                    InvalidArgumentsException("К этой команде ожидается аргумент через пробел - ID записи для удаления");
        }
        long id;
        try {
            id = Long.parseLong(args[1]);
        } catch (NumberFormatException e) {
            throw new
                    InvalidArgumentsException("Неверный формат ID. Ожидалось число, получено '%s'".formatted(args[1]));
        }
        if (!studentService.deleteById(id)) {
            throw new StudentNotFoundException("Студент с ID '%s' не найден".formatted(id));
        }
    }
}
