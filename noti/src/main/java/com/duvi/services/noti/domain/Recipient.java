package com.duvi.services.noti.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "recipients")
@Getter
@Setter
public class Recipient {
    @Id
    private String accountName;
    private String email;

    @OneToMany(mappedBy = "recipient")
    private Set<NotiSettings> notiSettings;
}
