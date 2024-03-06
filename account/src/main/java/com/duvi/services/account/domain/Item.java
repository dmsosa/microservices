package com.duvi.services.account.domain;


import com.duvi.services.account.domain.dto.ItemDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_name")
    private Account account;

    @NotNull
    @Length(min = 4, max = 20)
    private String title;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Currency currency;

    @NotNull
    private Frequency frequency;

    private ItemType type;

    @NotNull
    private String icon;



}