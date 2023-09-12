package org.itmo.bot.repository;

import org.itmo.bot.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Transactional
    @Query("select st from Student st where st.conversation.chatId = :chatId")
    Optional<Student> findByConversationChatId(@Param("chatId") Long chatId);

    @Modifying
    @Transactional
    @Query("update Student st set st.name = :name where st.conversation.chatId = :chatId")
    void setNameByChatId(String name, Long chatId);

    @Modifying
    @Transactional
    @Query("update Student st set st.surname = :surname where st.conversation.chatId = :chatId")
    void setSurnameByChatId(String surname, Long chatId);

    @Modifying
    @Transactional
    @Query("update Student st set st.ISU = ?1 where st.conversation.chatId = ?2")
    void setISUByChatId(Integer ISU, Long chatId);

    @Modifying
    @Transactional
    @Query("update Student st set st.itmoGroup = ?1 where st.conversation.chatId = ?2")
    void setGroupByChatId(String group, Long chatId);

    @Modifying
    @Transactional
    @Query("update Student st set st.isConfirmed = true where st.conversation.chatId = ?1")
    void confirm(Long chatId);
}
