package org.itmo.bot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itmo.bot.state.Conversation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="student")
public class Student implements CSVRepresentable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private Integer ISU;

    private String itmoGroup;

    private String tgNick;

    private Boolean isConfirmed = false;

    @OneToOne
    @JoinColumn(name="chat_id")
    private Conversation conversation;

    @OneToOne(cascade = CascadeType.ALL)
    private AfterPartyRegistration afterPartyRegistration;

    @Override
    public String representAsCSVRecord() {
        // Имя,Фамилия,Группа,Номер ИСУ,Ник телеграмм
        return "%s;%s;%s;%d;%s".formatted(name, surname, itmoGroup, ISU, tgNick);
    }
}
