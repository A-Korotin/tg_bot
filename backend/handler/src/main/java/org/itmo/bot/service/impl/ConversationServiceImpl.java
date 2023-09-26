package org.itmo.bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.repository.ConversationRepository;
import org.itmo.bot.service.ConversationService;
import org.itmo.bot.state.Conversation;
import org.itmo.bot.state.StateName;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {
    private final ConversationRepository conversationRepository;

    private final Set<String> organizerNames = new HashSet<>();
    {
        organizerNames.add("Korotin_A");
        organizerNames.add("damdinm");
        //organizerNames.add("bustuga4");
    }

    @Override
    public Conversation loadByChatId(Long chatId, String nickName) {
        Optional<Conversation> conversationOptional = conversationRepository.findById(chatId);
        if (conversationOptional.isPresent()) {
            return conversationOptional.get();
        }

        Conversation conversation = new Conversation();
        conversation.setChatId(chatId);
        StateName name = organizerNames.contains(nickName) ? StateName.ORGANIZER : StateName.START;
        conversation.setStateName(name);
        return conversationRepository.save(conversation);
    }

    @Override
    public void save(Conversation conversation) {
        conversationRepository.save(conversation);
    }

    @Override
    public void update(StateName stateName, Long chatId) {
        conversationRepository.updateConversationByChatId(stateName, chatId);
    }
}
