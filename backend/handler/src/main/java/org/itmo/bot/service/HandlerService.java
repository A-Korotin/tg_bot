package org.itmo.bot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itmo.bot.common.dto.PhotoMessageDTO;
import org.itmo.bot.common.dto.TextMessageDTO;
import org.itmo.bot.common.dto.TextResponseDTO;
import org.itmo.bot.common.topics.Topic;
import org.itmo.bot.state.Conversation;
import org.itmo.bot.state.StateFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HandlerService {

    private final KafkaTemplate<String, TextResponseDTO> template;
    private final ConversationService conversationService;

    private final StateFactory factory;

    @KafkaListener(topics = Topic.TEXT_MESSAGE_TOPIC, groupId = "my consumer")
    public void consume(TextMessageDTO dto) {
        log.info("Consumer consume Kafka message -> {}", dto.getMessage());
        Conversation conversation = conversationService.loadByChatId(dto.getChatId(), dto.getNickName());
        conversation.setStateFactory(factory);
        TextResponseDTO responseDTO = conversation.receive(dto);
        conversationService.update(conversation.getStateName(), conversation.getChatId());
        template.send(Topic.TEXT_RESPONSE_TOPIC, responseDTO);
    }

    @KafkaListener(topics = Topic.PHOTO_MESSAGE_TOPIC, groupId = "my consumer")
    public void consumePhoto(PhotoMessageDTO dto) {
        log.info("Consumer consume Kafka photo -> {}", dto.getPhotoId());
        Conversation conversation = conversationService.loadByChatId(dto.getChatId(), dto.getNickName());
        conversation.setStateFactory(factory);
        TextResponseDTO responseDTO = conversation.receive(dto);
        conversationService.update(conversation.getStateName(), conversation.getChatId());
        template.send(Topic.TEXT_RESPONSE_TOPIC, responseDTO);
    }
}
