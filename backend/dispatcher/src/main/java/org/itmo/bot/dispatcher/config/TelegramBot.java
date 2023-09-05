package org.itmo.bot.dispatcher.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itmo.bot.dispatcher.service.DispatcherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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

//    @KafkaListener(topics = "ANSWER", groupId = "Answer consumer")
//    public void answer(TextResponseDTO dto) throws TelegramApiException{
//        SendMessage sendMessage = new SendMessage(String.valueOf(dto.getChatId()),
//                dto.getMessage());
//        execute(sendMessage);
//    }

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
