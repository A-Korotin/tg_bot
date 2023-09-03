package org.itmo.bot.common.dto;

import lombok.Data;

@Data
public class AnswerDTO {
    public long chatId;
    public String message;
}
