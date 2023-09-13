package org.itmo.bot.repository;

import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.itmo.bot.model.AfterPartyRegistration;
import org.itmo.bot.model.Student;
import org.itmo.bot.state.Conversation;
import org.itmo.bot.state.StateName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AfterPartyRegistrationRepository extends JpaRepository<AfterPartyRegistration, Long> {


    @Transactional
    @Query("select ap from AfterPartyRegistration ap where ap.student.id = ?1")
    Optional<AfterPartyRegistration> getAfterPartyRegistrationByStudentId(Long studentId);


}
