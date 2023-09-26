package org.itmo.bot.command;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.common.dto.PhotoResponseDTO;
import org.itmo.bot.common.dto.TextResponseDTO;
import org.itmo.bot.common.topics.Topic;
import org.itmo.bot.exception.command.InvalidArgumentsException;
import org.itmo.bot.model.AfterPartyRegistration;
import org.itmo.bot.service.AfterPartyRegistrationService;
import org.itmo.bot.state.Conversation;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetPaidCommand extends Command {

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

        if (registration.getPaid()) {
            throw new InvalidArgumentsException("АП студента уже оплачено");
        }

        registration.setPaid(true);
        afterPartyRegistrationService.save(registration);

        sendConfirmationPhoto(chatId, registration);

        Conversation c;
        if ((c = registration.getStudent().getConversation()) != null)
        textTemplate.send(Topic.TEXT_RESPONSE_TOPIC, TextResponseDTO.builder()
                .chatId(c.getChatId())
                .message("Твоя оплата принята организаторами. Спасибо.")
                .build());
    }

    private void sendConfirmationPhoto(long chatId, AfterPartyRegistration registration) {
        if (registration.getPhotoId() == null) {
            textTemplate.send(Topic.TEXT_RESPONSE_TOPIC, TextResponseDTO.builder()
                    .chatId(chatId)
                    .message("Пользователь не отправил фотографию подтверждения").build());
            return;
        }

        photoResponse.send(Topic.PHOTO_RESPONSE_TOPIC, PhotoResponseDTO.builder()
                .photoId(registration.getPhotoId())
                .chatId(chatId)
                .build());
    }
}
