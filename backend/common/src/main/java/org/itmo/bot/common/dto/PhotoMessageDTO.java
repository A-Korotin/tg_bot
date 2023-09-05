package org.itmo.bot.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoMessageDTO {
    public long chatId;
    public String nickName;
    public String photoId;
}
