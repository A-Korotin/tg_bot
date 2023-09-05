package org.itmo.bot.state;

import lombok.Data;
import org.itmo.bot.common.dto.PhotoMessageDTO;
import org.itmo.bot.common.dto.TextMessageDTO;
import org.itmo.bot.common.dto.TextResponseDTO;

@Data
public abstract class State {
    protected Conversation conversation;

    public abstract TextResponseDTO receive(TextMessageDTO dto);
    public abstract TextResponseDTO receive(PhotoMessageDTO dto);


}
