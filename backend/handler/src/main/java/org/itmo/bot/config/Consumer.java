package org.itmo.bot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    @KafkaListener(topics = "DEFAULT", groupId = "my consumer")
    public void consume(String message) {
        log.info("Consumer consume Kafka message -> {}", message);

    }
}
