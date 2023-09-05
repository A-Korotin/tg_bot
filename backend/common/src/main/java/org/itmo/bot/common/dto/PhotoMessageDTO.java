package org.itmo.bot.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhotoMessageDTO {
    public long chatId;
    public String nickName;
    public String photoId;
}
