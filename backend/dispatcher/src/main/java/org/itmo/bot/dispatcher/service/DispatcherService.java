package org.itmo.bot.dispatcher.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface DispatcherService {
    void dispatch(Update update);
}
