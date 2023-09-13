package org.itmo.bot.state.afterparty;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.common.dto.PhotoMessageDTO;
import org.itmo.bot.common.dto.TextMessageDTO;
import org.itmo.bot.common.dto.TextResponseDTO;
import org.itmo.bot.model.AfterPartyRegistration;
import org.itmo.bot.model.Student;
import org.itmo.bot.service.AfterPartyRegistrationService;
import org.itmo.bot.service.StudentService;
import org.itmo.bot.service.impl.StudentServiceImpl;
import org.itmo.bot.state.State;
import org.itmo.bot.state.StateName;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class StateAfterPartyQuestion extends State {

    private final StudentServiceImpl studentService;

    @Override
    public TextResponseDTO receive(TextMessageDTO dto) {

        if (dto.getMessage().equals("Вернуться в начало")) {
            this.conversation.changeState(StateName.START);
            return this.conversation.getState().receive(dto);
        }

        if (dto.getMessage().equals("Не пойду")) {
            this.conversation.changeState(StateName.START);
            return this.conversation.getState().receive(dto);
        }

        if (dto.getMessage().equals("Пойду \uD83D\uDE0E")) {

            Student student = studentService.getStudentByChatId(dto.chatId);

            if (student != null) {
                AfterPartyRegistration afterPartyRegistration = new AfterPartyRegistration();

                afterPartyRegistration.setId(dto.getChatId());
                afterPartyRegistration.setPaid(false);

                student.setAfterPartyRegistration(afterPartyRegistration);

                studentService.save(student);

                this.conversation.setStateName(StateName.AFTER_PARTY_REGISTRATION_PHOTO_OF_PAID);


                return TextResponseDTO.builder()
                        .chatId(dto.getChatId())
                        .message("За афтерпати нужно платить \uD83D\uDE0B\uD83D\uDE0B \nЦена: 500 руб.\n\nНомер: +7123456678\nДэмдин!\n\nПосле оплаты пришли фотографию (или pdf???)")
                        .meta(List.of("Вернуться в начало"))
                        .build();
            }

        }




        return TextResponseDTO.builder()
                .chatId(dto.getChatId())
                .message("Мы очень рады, что ты зарегистрировался! Пойдёшь на афтерпати?")
                .meta(List.of("Не пойду", "Пойду \uD83D\uDE0E"))
                .build();


    }

    @Override
    public TextResponseDTO receive(PhotoMessageDTO dto) {
        return null;
    }
}
