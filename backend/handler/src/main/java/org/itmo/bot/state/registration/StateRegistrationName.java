package org.itmo.bot.state.registration;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.common.dto.PhotoMessageDTO;
import org.itmo.bot.common.dto.TextMessageDTO;
import org.itmo.bot.common.dto.TextResponseDTO;
import org.itmo.bot.model.Student;
import org.itmo.bot.service.StudentService;
import org.itmo.bot.state.State;
import org.itmo.bot.state.StateName;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class StateRegistrationName extends State {

    private final StudentService studentService;

    @Override
    public TextResponseDTO receive(TextMessageDTO dto) {
        if (dto.getMessage().equals("Вернуться в начало")) {
            this.conversation.changeState(StateName.START);
            return this.conversation.getState().receive(dto);
        }

        if (studentService.existsByChatId(dto.getChatId())) {
            try {
                studentService.setName(dto.getMessage(), dto.getChatId());
                this.conversation.changeState(StateName.REGISTRATION_SURNAME);
                return TextResponseDTO.builder()
                        .chatId(dto.getChatId())
                        .message("Пойдёт! Введи свою фамилию:")
                        .meta(List.of("Вернуться в начало"))
                        .build();

            } catch (IllegalArgumentException e) {
                return TextResponseDTO.builder()
                        .chatId(dto.getChatId())
                        .message("Странное у тебя имя! Попробуй ещё")
                        .meta(List.of("Вернуться в начало"))
                        .build();
            }
        }

        Student student = new Student();
        student.setConversation(this.conversation);
        studentService.save(student);
        studentService.setName(dto.getMessage(), dto.getChatId());

        this.conversation.changeState(StateName.REGISTRATION_SURNAME);
        return TextResponseDTO.builder()
                .chatId(dto.getChatId())
                .message("Пойдёт! Введи свою фамилию:")
                .meta(List.of("Вернуться в начало"))
                .build();
    }

    @Override
    public TextResponseDTO receive(PhotoMessageDTO dto) {
        return null;
    }
}
