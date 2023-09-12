package org.itmo.bot.service;

public interface CommandService {
    void execute(String command, long chatId);
}
