package org.itmo.bot.state;

import org.itmo.bot.common.dto.PhotoMessageDTO;
import org.itmo.bot.common.dto.TextMessageDTO;
import org.itmo.bot.common.dto.TextResponseDTO;

import java.util.List;

public class OrganizerState extends State {
    @Override
    public TextResponseDTO receive(TextMessageDTO dto) {
        if (dto.getMessage().equals("Button 1")) {
            this.conversation.setStateName(StateName.START);

        }

        return TextResponseDTO.builder()
                .chatId(dto.getChatId())
                .message("Hello from organizer state")
                .meta(List.of("Button 1", "Button 2"))
                .build();
    }

    @Override
    public TextResponseDTO receive(PhotoMessageDTO dto) {
        return null;
    }
}
