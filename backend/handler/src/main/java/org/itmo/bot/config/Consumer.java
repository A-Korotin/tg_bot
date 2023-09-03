package org.itmo.bot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itmo.bot.common.dto.AnswerDTO;
import org.itmo.bot.common.dto.MessageDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class Consumer {

    private final KafkaTemplate<String, AnswerDTO> template;

    @KafkaListener(topics = "DEFAULT", groupId = "my consumer")
    public void consume(MessageDTO dto) {

        log.info("Consumer consume Kafka message -> {}", dto.getMessage());
        AnswerDTO answerDto = new AnswerDTO();
        answerDto.setChatId(dto.getChatId());
        answerDto.setMessage("Answer using kafka");
        template.send("ANSWER", answerDto);
    }
}
