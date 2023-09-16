package org.itmo.bot.state;

import org.itmo.bot.common.dto.PhotoMessageDTO;
import org.itmo.bot.common.dto.TextMessageDTO;
import org.itmo.bot.common.dto.TextResponseDTO;
import org.itmo.bot.exception.command.CommandException;
import org.itmo.bot.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OrganizerState extends State {

    private final CommandService commandService;

    private static final String[] AVAILABLE_COMMANDS = {
            "ПолучитьВсех",
            "НаписатьВсем"
    };

    @Autowired
    public OrganizerState(@Qualifier("adminCommandServiceImpl") CommandService commandService) {
        this.commandService = commandService;
    }

    @Override
    public TextResponseDTO receive(TextMessageDTO dto) {
        if (dto.getMessage().equals("/start")) {
            return TextResponseDTO.builder()
                    .chatId(dto.getChatId())
                    .message("Привет, организатор.")
                    .meta(List.of(AVAILABLE_COMMANDS))
                    .build();
        }

        try {
            commandService.execute(dto.getMessage(), dto.getChatId());
        } catch (CommandException e) {
            return TextResponseDTO.builder()
                    .chatId(dto.getChatId())
                    .message(e.getMessage())
                    .meta(List.of(AVAILABLE_COMMANDS))
                    .build();
        }

        return TextResponseDTO.builder()
                .chatId(dto.getChatId())
                .message("Команда успешно выполнена.")
                .meta(List.of(AVAILABLE_COMMANDS))
                .build();
    }

    @Override
    public TextResponseDTO receive(PhotoMessageDTO dto) {
        return TextResponseDTO.builder()
                .chatId(dto.getChatId())
                .message("Обработка фотографий, отправленных администраторами, в данный момент не поддерживается.")
                .build();
    }
}
