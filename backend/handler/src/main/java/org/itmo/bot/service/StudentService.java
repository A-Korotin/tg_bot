package org.itmo.bot.service;


import org.itmo.bot.model.AfterPartyRegistration;
import org.itmo.bot.model.Student;

import java.util.List;

public interface StudentService {

    boolean existsByChatId(Long chatId);

    void save(Student student);

    void setName(String name, Long chatId) throws IllegalArgumentException;

    void setSurname(String surname, Long chatId) throws IllegalArgumentException;

    void setISU(Integer ISU, Long chatId) throws IllegalArgumentException;

    void setGroup(String group, Long chatId) throws IllegalArgumentException;

    void confirm(Long chatId);

    void setAfterPartyRegistration(AfterPartyRegistration afterPartyRegistration, Long chatId);

    Iterable<Student> findAllRegistered();

    Student getStudentByChatId(Long chatId);

    List<Long> getAllChatIds();
}
