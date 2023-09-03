package org.itmo.bot.dispatcher.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class Producer {

    private final KafkaTemplate<String, String> template;

    public void sendMessage(String message) {
        log.info("Sending message {}", message);

        template.send("DEFAULT", message);
    }
}
