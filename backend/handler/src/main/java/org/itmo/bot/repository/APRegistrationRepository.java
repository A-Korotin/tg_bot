package org.itmo.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface APRegistrationRepository extends JpaRepository<APRegistrationRepository, Long> {
}
