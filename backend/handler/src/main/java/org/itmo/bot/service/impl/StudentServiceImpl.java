package org.itmo.bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.model.Student;
import org.itmo.bot.repository.StudentRepository;
import org.itmo.bot.service.StudentService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public boolean existsByTGNick(String tgNick) {
        return studentRepository.findByTgNick(tgNick).isPresent();
    }

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public void setName(String name, Long chatId) throws IllegalArgumentException {
        if (name.length() > 25 || name.length() < 2) {
            throw new IllegalArgumentException();
        }

        studentRepository.setNameByChatId(name, chatId);
    }
}
