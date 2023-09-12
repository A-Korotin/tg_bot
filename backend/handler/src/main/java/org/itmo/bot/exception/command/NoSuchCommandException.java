package org.itmo.bot.exception.command;

public class NoSuchCommandException extends RuntimeException {
    public NoSuchCommandException(String message) {
        super(message);
    }
}
