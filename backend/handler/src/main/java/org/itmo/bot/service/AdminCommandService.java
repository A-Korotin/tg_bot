package org.itmo.bot.service;

public interface AdminCommandService {
    void execute(String command, long chatId);
}
