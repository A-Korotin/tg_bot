package org.itmo.bot.service.impl;

import org.itmo.bot.exception.command.AfterPartyConfigurationException;
import org.itmo.bot.service.AfterPartyConfigurationService;
import org.springframework.stereotype.Service;

@Service
public class AfterPartyConfigurationServiceImpl implements AfterPartyConfigurationService {

    private boolean enabled = false;

    @Override
    public void enableRegistration() {
        if (enabled) {
            throw new AfterPartyConfigurationException("Регистрация уже доступна!");
        }
        enabled = true;
    }

    @Override
    public boolean registrationEnabled() {
        return enabled;
    }
}
