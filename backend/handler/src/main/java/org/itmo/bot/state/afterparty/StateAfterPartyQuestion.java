package org.itmo.bot.state.afterparty;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.common.dto.PhotoMessageDTO;
import org.itmo.bot.common.dto.TextMessageDTO;
import org.itmo.bot.common.dto.TextResponseDTO;
import org.itmo.bot.model.AfterPartyRegistration;
import org.itmo.bot.model.Student;
import org.itmo.bot.service.impl.StudentServiceImpl;
import org.itmo.bot.state.State;
import org.itmo.bot.state.StateName;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class StateAfterPartyQuestion extends State {

    private final StudentServiceImpl studentService;

    @Override
    public TextResponseDTO receive(TextMessageDTO dto) {

        if (dto.getMessage().equals("Вернуться в начало")) {
            this.conversation.changeState(StateName.START);
            return this.conversation.getState().receive(dto);
        }

        if (dto.getMessage().equals("Не пойду")) {
            this.conversation.changeState(StateName.START);
            return this.conversation.getState().receive(dto);
        }

        if (dto.getMessage().equals("Пойду \uD83D\uDE0E")) {

            this.conversation.changeState(StateName.AFTER_PARTY_REGISTRATION_PHONE);
            return this.conversation.getState().receive(dto);

        }




        return TextResponseDTO.builder()
                .chatId(dto.getChatId())
                .message("Мы очень рады, что ты зарегистрировался! Пойдёшь на афтерпати?")
                .meta(List.of("Не пойду", "Пойду \uD83D\uDE0E"))
                .build();


    }

    @Override
    public TextResponseDTO receive(PhotoMessageDTO dto) {
        return TextResponseDTO.builder()
                .chatId(dto.getChatId())
                .message("Фото не поддерживается :)")
                .build();
    }
}
