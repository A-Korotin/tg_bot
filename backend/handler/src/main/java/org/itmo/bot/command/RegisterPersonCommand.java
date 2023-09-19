package org.itmo.bot.command;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.exception.command.InvalidArgumentsException;
import org.itmo.bot.model.AfterPartyRegistration;
import org.itmo.bot.model.Student;
import org.itmo.bot.service.StudentService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterPersonCommand extends Command {
    private final StudentService studentService;

    @Override
    public void execute(long chatId) {
        if (args.length != 2) {
            throw new
                InvalidArgumentsException("К этой команде ожидается аргумент {Имя} {Фамилия} {Ник Телеграм} {Телефон}");
        }
        String a = args[1];
        String[] arguments = a.split(" ");
        if (arguments.length != 4) {
            throw new
                InvalidArgumentsException("К этой команде ожидается аргумент {Имя} {Фамилия} {Ник Телеграм} {Телефон}");
        }

        Student student = new Student();
        student.setName(arguments[0]);
        student.setSurname(arguments[1]);
        student.setTgNick(arguments[2].replace("@", ""));
        AfterPartyRegistration registration = new AfterPartyRegistration();
        registration.setPhone(arguments[3]);
        registration.setPaid(true);
        student.setAfterPartyRegistration(registration);

        studentService.save(student);
    }
}
