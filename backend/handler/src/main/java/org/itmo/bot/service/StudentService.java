package org.itmo.bot.service;


import org.itmo.bot.model.Student;

public interface StudentService {

    boolean existsByTGNick(String tgNick);

    void save(Student student);

    void setName(String name, Long chatId) throws IllegalArgumentException;

    void setSurname(String surname, Long chatId) throws IllegalArgumentException;

    void setISU(Integer ISU, Long chatId) throws IllegalArgumentException;

    void setGroup(String group, Long chatId) throws IllegalArgumentException;
}
