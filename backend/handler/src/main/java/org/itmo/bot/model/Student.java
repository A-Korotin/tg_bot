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
public class Student {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String surname;

    private Integer ISU;

    private String group;

    private String tgNick;

    @OneToOne
    @JoinColumn(name="chat_id")
    private Conversation conversation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aPRegistration_id", referencedColumnName = "id")
    private APRegistration apRegistration;
}
