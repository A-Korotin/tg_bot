package org.itmo.bot.state;

import org.itmo.bot.common.dto.PhotoMessageDTO;
import org.itmo.bot.common.dto.TextMessageDTO;
import org.itmo.bot.common.dto.TextResponseDTO;

import java.util.ArrayList;

public class StartState extends State {
    @Override
    public TextResponseDTO receive(TextMessageDTO dto) {
        return TextResponseDTO.builder()
                .chatId(dto.getChatId())
                .message("Hello from start state")
                .meta(new ArrayList<>())
                .build();
    }

    @Override
    public TextResponseDTO receive(PhotoMessageDTO dto) {
        return null;
    }
}
