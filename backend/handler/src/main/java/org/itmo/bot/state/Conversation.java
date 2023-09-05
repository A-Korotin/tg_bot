package org.itmo.bot.state;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.itmo.bot.common.dto.PhotoMessageDTO;
import org.itmo.bot.common.dto.TextMessageDTO;
import org.itmo.bot.common.dto.TextResponseDTO;

@Entity
@Data
@Table(name = "conversation")
public class Conversation {
    @Id
    private Long chatId;

    @Enumerated(EnumType.STRING)
    private StateName stateName;

    @Transient
    @ToString.Exclude
    private State state;

    public TextResponseDTO receive(TextMessageDTO dto) {
        if (state == null) {
            state = StateFactory.getStatewithName(stateName);
            state.setConversation(this);
        }
        return state.receive(dto);
    }
    public TextResponseDTO receive(PhotoMessageDTO dto) {
        if (state == null) {
            state = StateFactory.getStatewithName(stateName);
            state.setConversation(this);
        }
        return state.receive(dto);
    }
}