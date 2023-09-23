package org.itmo.bot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "after_party_registration")
public class AfterPartyRegistration implements CSVRepresentable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photoId;

    @OneToOne(mappedBy = "afterPartyRegistration")
    @JoinColumn
    private Student student;

    private String phone;

    private Boolean paid = false;

    @Override
    public String representAsCSVRecord() {
        return "Да;%s;%s".formatted(phone, paid? "Да": "Нет");
    }
}