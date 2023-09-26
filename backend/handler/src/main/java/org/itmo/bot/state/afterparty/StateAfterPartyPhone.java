package org.itmo.bot.state.afterparty;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.common.dto.PhotoMessageDTO;
import org.itmo.bot.common.dto.TextMessageDTO;
import org.itmo.bot.common.dto.TextResponseDTO;
import org.itmo.bot.model.AfterPartyRegistration;
import org.itmo.bot.model.Student;
import org.itmo.bot.service.StudentService;
import org.itmo.bot.state.State;
import org.itmo.bot.state.StateName;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class StateAfterPartyPhone extends State {


    private final StudentService studentService;


    private boolean isValidPhoneNumber(String phoneNumber) {

        return phoneNumber.matches("^(\\+7|7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$");

    }

    @Override
    public TextResponseDTO receive(TextMessageDTO dto) {

        if (dto.getMessage().equals("Вернуться в начало")) {
            this.conversation.changeState(StateName.START);
            return this.conversation.getState().receive(dto);
        }

        if (isValidPhoneNumber(dto.getMessage())) {

            Student student = studentService.getStudentByChatId(dto.chatId);

            if (student != null) {
                AfterPartyRegistration afterPartyRegistration = new AfterPartyRegistration();

                afterPartyRegistration.setId(dto.getChatId());
                afterPartyRegistration.setPaid(false);
                afterPartyRegistration.setPhone(dto.getMessage());

                student.setAfterPartyRegistration(afterPartyRegistration);

                studentService.save(student);

                this.conversation.changeState(StateName.AFTER_PARTY_REGISTRATION_PHOTO_OF_PAID);
                return this.conversation.receive(dto);
            }

        }

        return TextResponseDTO.builder()
                .chatId(dto.getChatId())
                .message("Некорректный номер телефона! Пожалуйста, введи номер, как показано ниже: \n\n+77777777777\n87777777777\n77777777777")
                .meta(List.of("Вернуться в начало"))
                .build();
    }

    @Override
    public TextResponseDTO receive(PhotoMessageDTO dto) {
        return TextResponseDTO.builder()
                .chatId(dto.getChatId())
                .message("Фото не поддерживается :)")
                .build();
    }
}

