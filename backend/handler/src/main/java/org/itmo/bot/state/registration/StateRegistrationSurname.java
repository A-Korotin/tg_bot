package org.itmo.bot.state.registration;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.common.dto.PhotoMessageDTO;
import org.itmo.bot.common.dto.TextMessageDTO;
import org.itmo.bot.common.dto.TextResponseDTO;
import org.itmo.bot.service.StudentService;
import org.itmo.bot.state.State;
import org.itmo.bot.state.StateName;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class StateRegistrationSurname extends State {

    private final StudentService studentService;

    @Override
    public TextResponseDTO receive(TextMessageDTO dto) {
        if (dto.getMessage().equals("Вернуться в начало")) {
            this.conversation.changeState(StateName.START);
            return this.conversation.getState().receive(dto);
        }

        if (studentService.existsByChatId(dto.getChatId())) {
            try {
                studentService.setSurname(dto.getMessage(), dto.getChatId());
                this.conversation.changeState(StateName.REGISTRATION_ISU);
                return TextResponseDTO.builder()
                        .chatId(dto.getChatId())
                        .message("Пойдёт! Введи свой номер ИСУ:")
                        .meta(List.of("Вернуться в начало"))
                        .build();

            } catch (IllegalArgumentException e) {
                return TextResponseDTO.builder()
                        .chatId(dto.getChatId())
                        .message("Странная у тебя фамилия! Попробуй ещё")
                        .meta(List.of("Вернуться в начало"))
                        .build();
            }
        }

        this.conversation.changeState(StateName.REGISTRATION_NAME);

        return TextResponseDTO.builder()
                .chatId(dto.getChatId())
                .message("Начнём сначала!")
                .meta(List.of("Вернуться в начало"))
                .build();
    }

    @Override
    public TextResponseDTO receive(PhotoMessageDTO dto) {
        return TextResponseDTO.builder()
                .chatId(dto.getChatId())
                .message("Нормик, но введи свою фамилию уже!")
                .meta(List.of("Вернуться в начало"))
                .build();
    }
}
