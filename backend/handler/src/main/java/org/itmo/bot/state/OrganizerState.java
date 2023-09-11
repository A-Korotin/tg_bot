package org.itmo.bot.state;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.common.dto.PhotoMessageDTO;
import org.itmo.bot.common.dto.TextMessageDTO;
import org.itmo.bot.common.dto.TextResponseDTO;
import org.itmo.bot.exception.command.NoSuchCommandException;
import org.itmo.bot.service.AdminCommandService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class OrganizerState extends State {

    private final AdminCommandService commandService;

    @Override
    public TextResponseDTO receive(TextMessageDTO dto) {

        try {
            commandService.execute(dto.getMessage(), dto.getChatId());
        } catch (NoSuchCommandException e) {
            return TextResponseDTO.builder()
                    .chatId(dto.getChatId())
                    .message("Команда '%s' не найдена.".formatted(e.getMessage()))
                    .meta(List.of("Получить всех"))
                    .build();
        }

        return TextResponseDTO.builder()
                .chatId(dto.getChatId())
                .message("Команда успешно выполнена.")
                .meta(List.of("Получить всех"))
                .build();
    }

    @Override
    public TextResponseDTO receive(PhotoMessageDTO dto) {
        return null;
    }
}
