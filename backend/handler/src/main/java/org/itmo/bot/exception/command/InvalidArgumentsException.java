package org.itmo.bot.exception.command;

public class InvalidArgumentsException extends CommandException {
    public InvalidArgumentsException(String message) {
        super(message);
    }
}
