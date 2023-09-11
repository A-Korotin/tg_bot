package org.itmo.bot.command;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.common.dto.TextResponseDTO;
import org.itmo.bot.common.topics.Topic;
import org.itmo.bot.model.Student;
import org.itmo.bot.service.StudentService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class FindAllRegisteredStudentsCommand implements Command {
    private final StudentService studentService;
    private final KafkaTemplate<String, TextResponseDTO> template;

    @Override
    public void execute(long chatId) {
        Iterable<Student> students = studentService.findAllRegistered();

        List<String> representations = StreamSupport
                .stream(students.spliterator(), false)
                .map(Student::represent)
                .toList();

        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= representations.size(); ++i) {
            builder.append(i).append(": ").append(representations.get(i - 1)).append('\n');
        }
        if (builder.isEmpty()) {
            builder.append("Пока никого :(");
        }

        TextResponseDTO textResponseDTO = TextResponseDTO.builder()
                .chatId(chatId)
                .message(builder.toString())
                .build();

        template.send(Topic.TEXT_RESPONSE_TOPIC, textResponseDTO);
    }
}
