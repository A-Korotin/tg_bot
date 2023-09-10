package org.itmo.bot.state;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.common.dto.PhotoMessageDTO;
import org.itmo.bot.common.dto.TextMessageDTO;
import org.itmo.bot.common.dto.TextResponseDTO;
import org.itmo.bot.service.StudentService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class StateRegistrationISU extends State {

    private final StudentService studentService;

    @Override
    public TextResponseDTO receive(TextMessageDTO dto) {
        if (dto.getMessage().equals("Вернуться в начало")) {
            this.conversation.changeState(StateName.START);
            return this.conversation.getState().receive(dto);
        }

        if (studentService.existsByTGNick(dto.getNickName())) {
            try {
                studentService.setISU(Integer.valueOf(dto.getMessage()), dto.getChatId());
                this.conversation.changeState(StateName.REGISTRATION_ISU);
                return TextResponseDTO.builder()
                        .chatId(dto.getChatId())
                        .message("Пойдёт! Введи свой номер группы:")
                        .meta(List.of("Вернуться в начало"))
                        .build();

            } catch (IllegalArgumentException | ClassCastException e) {
                return TextResponseDTO.builder()
                        .chatId(dto.getChatId())
                        .message("Странное у тебя ИСУ! Попробуй ещё")
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
        return null;
    }
}
