package org.itmo.bot.service;

import org.itmo.bot.state.Conversation;

public interface ConversationService {
    Conversation loadByChatId(Long chatId, String nickName);

    void save(Conversation conversation);

}
