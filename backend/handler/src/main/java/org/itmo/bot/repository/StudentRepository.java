package org.itmo.bot.repository;

import org.itmo.bot.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByTgNick(String tgNick);

    @Modifying
    @Query("update Student st set st.name = ?1 where st.conversation.chatId = ?2")
    void setNameByChatId(String name, Long chatId);

    @Modifying
    @Query("update Student st set st.surname = ?1 where st.conversation.chatId = ?2")
    void setSurnameByChatId(String surname, Long chatId);

    @Modifying
    @Query("update Student st set st.ISU = ?1 where st.conversation.chatId = ?2")
    void setISUByChatId(Integer ISU, Long chatId);

    @Modifying
    @Query("update Student st set st.group = ?1 where st.conversation.chatId = ?2")
    void setGroupByChatId(String group, Long chatId);
}
