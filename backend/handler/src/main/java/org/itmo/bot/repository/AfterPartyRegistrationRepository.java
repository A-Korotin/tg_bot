package org.itmo.bot.repository;

import org.itmo.bot.model.AfterPartyRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AfterPartyRegistrationRepository extends JpaRepository<AfterPartyRegistration, Long> {


    @Transactional
    @Query("select ap from AfterPartyRegistration ap where ap.student.id = ?1")
    Optional<AfterPartyRegistration> getAfterPartyRegistrationByStudentId(Long studentId);


}
