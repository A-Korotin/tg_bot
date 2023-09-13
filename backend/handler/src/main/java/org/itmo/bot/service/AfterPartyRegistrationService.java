package org.itmo.bot.service;

import org.itmo.bot.model.AfterPartyRegistration;

import java.util.Optional;

public interface AfterPartyRegistrationService {
    void save(AfterPartyRegistration afterPartyRegistration);
    Optional<AfterPartyRegistration> getAfterPartyRegistrationByStudentId(Long studentId);
}
