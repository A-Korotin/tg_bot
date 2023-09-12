package org.itmo.bot.repository;

import org.itmo.bot.state.Conversation;
import org.itmo.bot.state.StateName;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ConversationRepository extends CrudRepository<Conversation, Long> {

    @Modifying
    @Transactional
    @Query("update Conversation cn set cn.stateName = :state where cn.chatId = :id")
    void updateConversationByChatId(@Param("state") StateName stateName, @Param("id") Long chatId);

}
