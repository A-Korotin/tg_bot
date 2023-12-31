package org.itmo.bot.dispatcher.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itmo.bot.common.dto.FileResponseDTO;
import org.itmo.bot.common.dto.PhotoResponseDTO;
import org.itmo.bot.common.dto.TextResponseDTO;
import org.itmo.bot.common.topics.Topic;
import org.itmo.bot.dispatcher.service.DispatcherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final TelegramBotsApi telegramBotsApi;
    private final DispatcherService dispatcherService;

    @Value("${telegram.bot.username}")
    private String username;

    @Value("${telegram.bot.token}")
    private String token;

    @PostConstruct
    public void postConstruct() throws TelegramApiException {
        this.telegramBotsApi.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        String name = update.getMessage().getChat().getUserName();
        log.info("Dispatching message from {}", name);
        dispatcherService.dispatch(update);
    }

    @KafkaListener(topics = Topic.TEXT_RESPONSE_TOPIC, groupId = "Answer consumer")
    public void answer(TextResponseDTO dto) throws TelegramApiException{
        SendMessage sendMessage =
                new SendMessage(String.valueOf(dto.getChatId()),
                dto.getMessage());
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.addAll(dto.getMeta().subList(0, Math.min(dto.getMeta().size(), 4)));
        rows.add(row);
        if (dto.getMeta().size() > 4) {
            row = new KeyboardRow();
            row.addAll(dto.getMeta().subList(4, dto.getMeta().size()));
            rows.add(row);
        }
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(rows);
        markup.setResizeKeyboard(true);
        sendMessage.setReplyMarkup(markup);
        execute(sendMessage);

    }

    @KafkaListener(topics = Topic.PHOTO_RESPONSE_TOPIC, groupId = "Answer consumer")
    public void answerPhoto(PhotoResponseDTO dto) throws TelegramApiException{
        SendPhoto sendPhoto =
                new SendPhoto();
        sendPhoto.setChatId(dto.getChatId());
        sendPhoto.setPhoto(new InputFile(dto.getPhotoId()));
        execute(sendPhoto);
    }

    @KafkaListener(topics = Topic.FILE_RESPONSE_TOPIC, groupId = "Answer consumer")
    public void answerFile(FileResponseDTO dto) throws TelegramApiException {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(dto.getChatId());
        InputStream inputStream = new ByteArrayInputStream(dto.getContent());
        sendDocument.setDocument(new InputFile(inputStream, dto.getFileName()));
        sendDocument.setCaption(dto.getCaption());

        execute(sendDocument);
    }



    @Override
    @SuppressWarnings("deprecated")
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }
}
