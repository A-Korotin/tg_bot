package org.itmo.bot.state;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.common.dto.PhotoMessageDTO;
import org.itmo.bot.common.dto.TextMessageDTO;
import org.itmo.bot.common.dto.TextResponseDTO;
import org.itmo.bot.model.Student;
import org.itmo.bot.service.AfterPartyConfigurationService;
import org.itmo.bot.service.StudentService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class StartState extends State {

    private final StudentService studentService;
    private final AfterPartyConfigurationService afterPartyConfigurationService;

    @Override
    public TextResponseDTO receive(TextMessageDTO dto) {

        Student student = studentService.getStudentByChatId(dto.getChatId());

        if (dto.getMessage().equals("Зарегистрироваться на посвят \uD83D\uDE0B")) {

            if (student == null || !student.getIsConfirmed()) {
                this.conversation.changeState(StateName.REGISTRATION_NAME);
                return TextResponseDTO.builder()
                        .chatId(dto.getChatId())
                        .message("Введи своё имя:")
                        .meta(List.of("Вернуться в начало"))
                        .build();
            }

            else {
                return TextResponseDTO.builder()
                        .chatId(dto.getChatId())
                        .message("Ты уже зарегистрирован")
                        .build();
            }


        }


        if (dto.getMessage().equals("Я иду на афтерпати \uD83E\uDEA9") &&
                afterPartyConfigurationService.registrationEnabled()) {
            if (student != null) {
                if (student.getIsConfirmed() && student.getAfterPartyRegistration() == null) {

                    this.conversation.changeState(StateName.AFTER_PARTY_REGISTRATION_QUESTION);
                    return this.conversation.getState().receive(dto);
                }
            }
        }



        List<String> metaForMessage = new ArrayList<>();
        metaForMessage.add("Зарегистрироваться на посвят \uD83D\uDE0B");


        if (student != null && afterPartyConfigurationService.registrationEnabled()) {
            if (student.getIsConfirmed() && student.getAfterPartyRegistration() == null) {
                metaForMessage.add("Я иду на афтерпати \uD83E\uDEA9");
            }
        }

        return TextResponseDTO.builder()
                .chatId(dto.getChatId())
                .message("Привет! \uD83D\uDC4B\nЯ бот для посвящения в первокурсники факультета СУИР \uD83D\uDE03")
                .meta(metaForMessage)
                .build();
    }

    @Override
    public TextResponseDTO receive(PhotoMessageDTO dto) {
        return TextResponseDTO.builder()
                .chatId(dto.getChatId())
                .message("Привет! \uD83D\uDC4B\nЯ бот для посвящения в первокурсники факультета СУИР \uD83D\uDE03")
                .meta(List.of("Зарегистрироваться на посвят \uD83D\uDE0B"))
                .build();
    }
}
