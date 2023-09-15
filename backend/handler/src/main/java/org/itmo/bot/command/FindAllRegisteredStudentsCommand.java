package org.itmo.bot.command;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.common.dto.FileResponseDTO;
import org.itmo.bot.common.topics.Topic;
import org.itmo.bot.model.CSVRepresentable;
import org.itmo.bot.model.Student;
import org.itmo.bot.service.StudentService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class FindAllRegisteredStudentsCommand extends Command {
    private final StudentService studentService;
    private final KafkaTemplate<String, FileResponseDTO> template;

    @Override
    public void execute(long chatId) {
        Iterable<Student> students = studentService.findAllRegistered();

        StringBuilder builder = new StringBuilder();
        builder.append('\ufeff'); // BOM for Excel UTF-8 encoding
        builder.append("Имя;Фамилия;Группа;Номер ИСУ;Ник телеграмм\n");
        for (CSVRepresentable representable: students) {
            builder.append(representable.representAsCSVRecord()).append('\n');
        }


        FileResponseDTO dto = FileResponseDTO.builder()
                .chatId(chatId)
                .caption("Лови!")
                .fileName("registrations.csv")
                .content(builder.toString().getBytes(StandardCharsets.UTF_8))
                .build();

        template.send(Topic.FILE_RESPONSE_TOPIC, dto);
    }
}
