package org.itmo.bot.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TextResponseDTO {
    public long chatId;
    public String message;
}
