package org.itmo.bot.dispatcher.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.itmo.bot.dispatcher.service.Producer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final TelegramBotsApi telegramBotsApi;
    private final Producer producer;

    @Value("${telegram.bot.username}")
    private String username;

    @Value("${telegram.bot.token}")
    private String token;

    @PostConstruct
    public void postConstruct() throws TelegramApiException {
        this.telegramBotsApi.registerBot(this);
    }


    @SneakyThrows(TelegramApiException.class)
    @Override
    public void onUpdateReceived(Update update) {
        String text = update.getMessage().getText();
        String name = update.getMessage().getChat().getUserName();
        log.info("Received message from {}: {}", name, text);
        SendMessage sendMessage = new SendMessage(String.valueOf(update.getMessage().getChatId()), "Answer");
        executeAsync(sendMessage);
        producer.sendMessage(text);
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }
}
