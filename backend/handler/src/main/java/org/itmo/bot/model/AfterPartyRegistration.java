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
@Table(name = "after_party_registration")
public class AfterPartyRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photoId;

    @OneToOne(mappedBy = "afterPartyRegistration")
    @JoinColumn
    private Student student;

    private Boolean paid;
}