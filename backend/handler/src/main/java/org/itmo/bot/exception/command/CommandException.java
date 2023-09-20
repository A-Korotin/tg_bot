package org.itmo.bot.exception.command;

public class CommandException extends RuntimeException{
    public CommandException(String message) {
        super(message);
    }
}
