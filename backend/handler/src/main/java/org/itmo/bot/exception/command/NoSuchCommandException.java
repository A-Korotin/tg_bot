package org.itmo.bot.exception.command;

public class NoSuchCommandException extends CommandException {
    public NoSuchCommandException(String message) {
        super(message);
    }
}
