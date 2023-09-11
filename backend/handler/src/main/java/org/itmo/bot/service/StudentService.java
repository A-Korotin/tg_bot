package org.itmo.bot.service;


import org.itmo.bot.model.Student;

public interface StudentService {

    boolean existsByTGNick(String tgNick);

    void save(Student student);

    void setName(String name, Long chatId) throws IllegalArgumentException;

    Iterable<Student> findAllRegistered();
}
