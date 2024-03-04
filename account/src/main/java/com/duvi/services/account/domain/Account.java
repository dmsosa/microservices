package com.duvi.services.account.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "accounts")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    private String name;

    private LocalDateTime lastSeen;

    @Valid
    @OneToMany(mappedBy = "account")
    private Set<Item> incomes;

    @Valid
    @OneToMany(mappedBy = "account")
    private Set<Item> expenses;

    @NotNull
    @Length(min = 3, max = 20_000)
    private String note;
}
