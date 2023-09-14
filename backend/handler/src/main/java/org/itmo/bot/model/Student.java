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
public class Student implements Representable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String surname;

    private Integer ISU;

    private String itmoGroup;

    private String tgNick;

    @Column(columnDefinition = "boolean default false")
    private Boolean isConfirmed;

    @OneToOne
    @JoinColumn(name="chat_id")
    private Conversation conversation;

    @OneToOne(cascade = CascadeType.ALL)
    private AfterPartyRegistration afterPartyRegistration;

    @Override
    public String represent() {
        return "%d %s %s %s %s".formatted(ISU, name, surname, itmoGroup, tgNick);
    }
}
