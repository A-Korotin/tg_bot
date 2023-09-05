package org.itmo.bot.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhotoResponseDTO {
    public long chatId;
    public String photoId;
}
