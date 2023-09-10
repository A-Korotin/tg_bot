package org.itmo.bot.repository;

import org.itmo.bot.model.APRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface APRegistrationRepository extends JpaRepository<APRegistration, Long> {
}
