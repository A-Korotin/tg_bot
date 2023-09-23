package org.itmo.bot.command;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.common.dto.PhotoResponseDTO;
import org.itmo.bot.common.dto.TextResponseDTO;
import org.itmo.bot.common.topics.Topic;
import org.itmo.bot.exception.command.InvalidArgumentsException;
import org.itmo.bot.model.AfterPartyRegistration;
import org.itmo.bot.service.AfterPartyRegistrationService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCheckCommand extends Command {
    private final AfterPartyRegistrationService afterPartyRegistrationService;
    private final KafkaTemplate<String, TextResponseDTO> textTemplate;
    private final KafkaTemplate<String, PhotoResponseDTO> photoResponse;

    @Override
    public void execute(long chatId) {
        if (args.length != 2) {
            throw new InvalidArgumentsException("Ожидался аргумент - ID регистрации");
        }
        long id;
        try {
            id = Long.parseLong(args[1]);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException("Ожидался ID регистрации, получено '%s'".formatted(args[1]));
        }

        AfterPartyRegistration registration =
                afterPartyRegistrationService.getAfterPartyRegistrationByStudentId(id)
                        .orElseThrow(() -> new InvalidArgumentsException("Студент не найден, либо не имеет регистрации на АП"));

        if (registration.getPhotoId() != null) {
            photoResponse.send(Topic.PHOTO_RESPONSE_TOPIC, PhotoResponseDTO.builder()
                    .chatId(chatId)
                    .photoId(registration.getPhotoId())
                    .build());
            return;
        }

        textTemplate.send(Topic.TEXT_RESPONSE_TOPIC, TextResponseDTO.builder()
                .chatId(chatId)
                .message("Пользователь не прислал чек об оплате")
                .build());
    }
}
