package org.itmo.bot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itmo.bot.common.dto.TextResponseDTO;
import org.itmo.bot.common.dto.TextMessageDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class Consumer {

    private final KafkaTemplate<String, TextResponseDTO> template;

    @KafkaListener(topics = "DEFAULT", groupId = "my consumer")
    public void consume(TextMessageDTO dto) {

        log.info("Consumer consume Kafka message -> {}", dto.getMessage());
        TextResponseDTO answerDto = TextResponseDTO.builder()
                .chatId(dto.getChatId())
                .message("Answer using kafka").build();
        template.send("ANSWER", answerDto);
    }
}
