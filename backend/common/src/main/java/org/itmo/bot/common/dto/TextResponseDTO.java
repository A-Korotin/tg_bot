package org.itmo.bot.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextResponseDTO {
    public long chatId;
    public String message;
    @Builder.Default
    public List<String> meta = new ArrayList<>();
}
