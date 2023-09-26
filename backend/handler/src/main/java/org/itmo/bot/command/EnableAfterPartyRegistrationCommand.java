package org.itmo.bot.command;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.service.AfterPartyConfigurationService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnableAfterPartyRegistrationCommand extends Command {
    private final AfterPartyConfigurationService afterPartyConfigurationService;

    @Override
    public void execute(long chatId) {
        afterPartyConfigurationService.enableRegistration();
    }
}
