package org.itmo.bot.repository;

import org.itmo.bot.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


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

    @Modifying
    @Transactional
    @Query("update Student st set st.afterPartyRegistration.id = ?1 where st.id = ?2")
    void setAfterPartyRegistrationByStudentId(Long afterPartyRegistrationId, Long studentId);

    @Modifying
    @Transactional
    @Query("update Student st set st.isPresent = true where st.id = ?1")
    void setPresent(Long id);

    @Query(value = "select s.chat_id from student s where s.is_present and s.chat_id is not null", nativeQuery = true)
    List<Long> findAllPresentChatIds();

}
