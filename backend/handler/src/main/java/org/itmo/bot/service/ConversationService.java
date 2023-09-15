package org.itmo.bot.service;

import org.itmo.bot.state.Conversation;
import org.itmo.bot.state.StateName;

public interface ConversationService {
    Conversation loadByChatId(Long chatId, String nickName);

    void save(Conversation conversation);

    void update(StateName stateName, Long chatId);

}
