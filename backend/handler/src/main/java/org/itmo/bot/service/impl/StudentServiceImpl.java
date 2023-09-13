package org.itmo.bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.model.AfterPartyRegistration;
import org.itmo.bot.model.Student;
import org.itmo.bot.repository.StudentRepository;
import org.itmo.bot.service.AfterPartyRegistrationService;
import org.itmo.bot.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public boolean existsByChatId(Long chatId) {
        return studentRepository.findByConversationChatId(chatId).isPresent();
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

    @Override
    public void confirm(Long chatId) {
        studentRepository.confirm(chatId);
    }



    @Override
    public Iterable<Student> findAllRegistered() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentByChatId(Long chatId) {

        Optional<Student> student = studentRepository.findByConversationChatId(chatId);

        return student.orElse(null);

    }

    @Override
    public void setAfterPartyRegistration(AfterPartyRegistration afterPartyRegistration, Long chatID) {

        Student student = studentRepository.findByConversationChatId(chatID).orElse(null);

        if (student != null) {
            studentRepository.setAfterPartyRegistrationByStudentId(afterPartyRegistration.getId(), student.getId());
        }

    }
}