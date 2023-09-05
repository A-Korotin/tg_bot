package org.itmo.bot.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TextMessageDTO {
    public long chatId;
    public String nickName;
    public String message;
}
