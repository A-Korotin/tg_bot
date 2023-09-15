package org.itmo.bot.command;

@FunctionalInterface
public interface Command {
    void execute(long chatId);
}
