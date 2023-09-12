package org.itmo.bot.state;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.common.dto.PhotoMessageDTO;
import org.itmo.bot.common.dto.TextMessageDTO;
import org.itmo.bot.common.dto.TextResponseDTO;
import org.itmo.bot.service.CommandService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class StartState extends State {

    @Override
    public TextResponseDTO receive(TextMessageDTO dto) {
        if (dto.getMessage().equals("Зарегистрироваться на посвят")) {
            this.conversation.changeState(StateName.REGISTRATION_NAME);
            return TextResponseDTO.builder()
                    .chatId(dto.getChatId())
                    .message("Введи своё имя:")
                    .meta(List.of("Вернуться в начало"))
                    .build();
        }

        return TextResponseDTO.builder()
                .chatId(dto.getChatId())
                .message("Привет! Я бот для посвящения в первокурсники факультета СУИР")
                .meta(List.of("Зарегистрироваться на посвят"))
                .build();
    }

    @Override
    public TextResponseDTO receive(PhotoMessageDTO dto) {
        return null;
    }
}
