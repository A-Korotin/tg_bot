package org.itmo.bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.model.AfterPartyRegistration;
import org.itmo.bot.repository.AfterPartyRegistrationRepository;
import org.itmo.bot.service.AfterPartyRegistrationService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AfterPartyRegistrationServiceImpl implements AfterPartyRegistrationService {

    private final AfterPartyRegistrationRepository afterPartyRegistrationRepository;

    @Override
    public void save(AfterPartyRegistration afterPartyRegistration) {
        afterPartyRegistrationRepository.save(afterPartyRegistration);
    }

    @Override
    public Optional<AfterPartyRegistration> getAfterPartyRegistrationByStudentId(Long studentId) {
        return afterPartyRegistrationRepository.getAfterPartyRegistrationByStudentId(studentId);
    }


}
