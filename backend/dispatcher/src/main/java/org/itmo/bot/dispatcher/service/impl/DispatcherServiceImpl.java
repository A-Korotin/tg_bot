package org.itmo.bot.dispatcher.service.impl;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.common.dto.PhotoMessageDTO;
import org.itmo.bot.common.dto.TextMessageDTO;
import org.itmo.bot.common.topics.Topic;
import org.itmo.bot.dispatcher.service.DispatcherService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;


@Service
@RequiredArgsConstructor
public class DispatcherServiceImpl implements DispatcherService {

    private final KafkaTemplate<String, TextMessageDTO> textTemplate;
    private final KafkaTemplate<String, PhotoMessageDTO> photoTemplate;

    @Override
    public void dispatch(Update update) {
        long chatId = update.getMessage().getChatId();
        String nickName = update.getMessage().getChat().getUserName();

        if (update.getMessage().hasPhoto()) {
            PhotoMessageDTO dto = PhotoMessageDTO.builder()
                    .chatId(chatId)
                            .nickName(nickName)
                                    .photoId(update.getMessage().getPhoto().get(0).getFileId())
                    .build();
            photoTemplate.send(Topic.PHOTO_MESSAGE_TOPIC, dto);
        }

        if (update.getMessage().hasText()) {
            TextMessageDTO dto = TextMessageDTO.builder()
                    .chatId(chatId)
                    .nickName(nickName)
                    .message(update.getMessage().getText())
                    .build();

            textTemplate.send(Topic.TEXT_MESSAGE_TOPIC, dto);
        }
    }
}
