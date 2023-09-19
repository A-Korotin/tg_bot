package org.itmo.bot.exception.command;

public class StudentNotFoundException extends CommandException{
    public StudentNotFoundException(String message) {
        super(message);
    }
}
