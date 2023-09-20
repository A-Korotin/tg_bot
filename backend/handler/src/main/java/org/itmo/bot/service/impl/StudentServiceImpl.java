package org.itmo.bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.itmo.bot.model.AfterPartyRegistration;
import org.itmo.bot.model.Student;
import org.itmo.bot.repository.StudentRepository;
import org.itmo.bot.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final String GROUP_RE = "R31\\d\\d";
    private final String NAME_RE = "^[а-яА-Яa-zA-Z]+$";

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
        if (name.length() > 25 || name.length() < 2 || !name.trim().matches(NAME_RE)) {
            throw new IllegalArgumentException();
        }

        studentRepository.setNameByChatId(name, chatId);
    }

    @Override
    public void setSurname(String surname, Long chatId) throws IllegalArgumentException {
        if (surname.length() > 25 || surname.length() < 2 || !surname.trim().matches(NAME_RE)) {
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
        if (!group.trim().matches(GROUP_RE)) {
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
    public List<Long> getAllChatIds() {
        return studentRepository.findAll().stream().filter(s -> s.getConversation() != null)
                .map(s -> s.getConversation().getChatId()).collect(Collectors.toList());
    }

    @Override
    public void setAfterPartyRegistration(AfterPartyRegistration afterPartyRegistration, Long chatID) {

        Student student = studentRepository.findByConversationChatId(chatID).orElse(null);

        if (student != null) {
            studentRepository.setAfterPartyRegistrationByStudentId(afterPartyRegistration.getId(), student.getId());
        }

    }

    @Override
    public boolean deleteById(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            return false;
        }
        studentRepository.deleteById(studentId);
        return true;
    }
}
