package org.itmo.bot.command;


public abstract class Command {
    protected String[] args;

    public void setArgs(String[] args) {
        this.args = args;
    }

    public abstract void execute(long chatId);
}
