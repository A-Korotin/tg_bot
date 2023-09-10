package org.itmo.bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.model.Student;
import org.itmo.bot.repository.StudentRepository;
import org.itmo.bot.service.StudentService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

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

    @Override
    public void setSurname(String surname, Long chatId) throws IllegalArgumentException {
        if (surname.length() > 25 || surname.length() < 2) {
            throw new IllegalArgumentException();
        }

        studentRepository.setSurnameByChatId(surname, chatId);
    }

    @Override
    public void setISU(Integer ISU, Long chatId) throws IllegalArgumentException {
        if (ISU / 100000 < 1 || ISU / 100000 > 9) {
            throw new IllegalArgumentException();
        }

        studentRepository.setISUByChatId(ISU, chatId);
    }

    @Override
    public void setGroup(String group, Long chatId) throws IllegalArgumentException {
        if (!Character.isLetter(group.charAt(0)) || !Character.isDigit(group.charAt(1))) {
            throw new IllegalArgumentException();
        }

        studentRepository.setGroupByChatId(group, chatId);
    }
}
