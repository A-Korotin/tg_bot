package org.itmo.bot.common.dto;

import lombok.Data;

@Data
public class MessageDTO {
    public long chatId;
    public String message;
}
