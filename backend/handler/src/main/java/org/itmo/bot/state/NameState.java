package org.itmo.bot.state;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.common.dto.PhotoMessageDTO;
import org.itmo.bot.common.dto.TextMessageDTO;
import org.itmo.bot.common.dto.TextResponseDTO;
import org.itmo.bot.service.StudentService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class NameState extends State {

    private final StudentService studentService;
    @Override
    public TextResponseDTO receive(TextMessageDTO dto) {
        if (studentService.existsByTGNick(dto.getNickName())) {
            try {
                studentService.setName(dto.getMessage(), dto.getChatId());
            } catch (IllegalArgumentException e) {
                this.conversation.setStateName(StateName.START);
                return TextResponseDTO.builder()
                        .chatId(dto.getChatId())
                        .message("Странное у тебя имя!")
                        .build();
            }
        }

        this.conversation.setStateName(StateName.REGISTRATION_SURNAME);
        return TextResponseDTO.builder()
                .chatId(dto.getChatId())
                .build();
    }

    @Override
    public TextResponseDTO receive(PhotoMessageDTO dto) {
        return null;
    }
}
