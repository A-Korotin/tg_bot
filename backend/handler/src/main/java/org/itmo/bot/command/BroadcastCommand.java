package org.itmo.bot.command;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.common.dto.TextResponseDTO;
import org.itmo.bot.common.topics.Topic;
import org.itmo.bot.exception.command.InvalidArgumentsException;
import org.itmo.bot.model.Student;
import org.itmo.bot.service.StudentService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BroadcastCommand extends Command {
    private final StudentService studentService;
    private final KafkaTemplate<String, TextResponseDTO> template;

    @Override
    public void execute(long chatId) {
        if (args.length != 2) {
            throw new InvalidArgumentsException("К этой команде ожидается аргумент через пробел");
        }
        String message = args[1];

        Iterable<Student> students = studentService.findAllRegistered();
        for(Student student: students){
            if (student.getConversation() == null) { // зарегистрирован через админов
                continue;
            }
            TextResponseDTO responseDTO = TextResponseDTO.builder()
                    .chatId(student.getConversation().getChatId())
                    .message(message)
                    .build();
            template.send(Topic.TEXT_RESPONSE_TOPIC, responseDTO);
        }
    }
}
