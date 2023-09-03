package org.itmo.bot.dispatcher.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itmo.bot.common.dto.MessageDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class Producer {

    private final KafkaTemplate<String, MessageDTO> template;

    public void sendMessage(MessageDTO dto) {
        log.info("Sending message {} to chat id {}", dto.getMessage(), dto.getChatId());

        template.send("DEFAULT", dto);
    }
}
