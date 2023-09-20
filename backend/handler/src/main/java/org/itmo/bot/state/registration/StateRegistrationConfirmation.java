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
public class StateRegistrationConfirmation extends State {

    private final StudentService studentService;

    @Override
    public TextResponseDTO receive(TextMessageDTO dto) {
        if (dto.getMessage().equals("Вернуться в начало")) {
            this.conversation.changeState(StateName.START);
            return this.conversation.getState().receive(dto);
        }

        if (studentService.existsByChatId(dto.getChatId())) {
            if (!dto.getMessage().equals("СОГЛАСЕН")) {
                return TextResponseDTO.builder()
                        .chatId(dto.getChatId())
                        .message("Ну доверься... Что ты сразу??...")
                        .meta(List.of("Вернуться в начало"))
                        .build();
            }
            studentService.confirm(dto.getChatId());
            this.conversation.changeState(StateName.START); //TODO state REGISTRATION_PASSED
            return TextResponseDTO.builder()
                    .chatId(dto.getChatId())
                    .message("Умничка! Можешь кайфовать! Ты зареган!")
                    .meta(List.of("Вернуться в начало"))
                    .build();
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
                .message("ну не надо фоток...")
                .meta(List.of("Вернуться в начало", "СОГЛАСЕН"))
                .build();
    }
}
