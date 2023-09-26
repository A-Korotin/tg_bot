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
public class StateAfterPartyPhotoOfPaid extends State {

    private final AfterPartyRegistrationService afterPartyRegistrationService;
    private final StudentService studentService;

    @Override
    public TextResponseDTO receive(TextMessageDTO dto) {

        if (dto.getMessage().equals("Вернуться в начало")) {
            this.conversation.changeState(StateName.START);
            return this.conversation.getState().receive(dto);
        }

        return TextResponseDTO.builder()
                .chatId(dto.getChatId())
                .message("""
                        Для того, чтобы попасть на афтерпати, нужно заплатить! \uD83E\uDD11

                        Цена: 450₽\s
                        Карта: 89148320636 (Дамдин Максимович М.) СБЕР
                        В сообщении напиши свои фамилию и имя!\s

                        После оплаты пришли скрин с чеком""")
                .meta(List.of("Вернуться в начало"))
                .build();
    }

    @Override
    public TextResponseDTO receive(PhotoMessageDTO dto) {


        Student student = studentService.getStudentByChatId(dto.getChatId());

        if (student != null) {

            AfterPartyRegistration afterPartyRegistration =
                    afterPartyRegistrationService
                            .getAfterPartyRegistrationByStudentId(student.getId())
                            .orElse(null);


            if (afterPartyRegistration != null) {
                afterPartyRegistration.setPhotoId(dto.photoId);
                studentService.setAfterPartyRegistration(afterPartyRegistration, dto.getChatId());


                afterPartyRegistrationService.save(afterPartyRegistration);

                this.conversation.setStateName(StateName.START);

                return TextResponseDTO.builder()
                        .chatId(dto.getChatId())
                        .message("Отлично! Наша команда проверит пополнение и мы тебе сообщим статус заявки")
                        .meta(List.of("Вернуться в начало"))
                        .build();

            }


        }


        return TextResponseDTO.builder()
                .chatId(dto.getChatId())
                .message("Что-то пошло не так")
                .meta(List.of())
                .build();


    }
}
