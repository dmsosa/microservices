package com.duvi.services.account.model;


import com.duvi.services.account.model.dto.ItemDTO;
import com.duvi.services.account.model.enums.Category;
import com.duvi.services.account.model.enums.Currency;
import com.duvi.services.account.model.enums.Frequency;
import com.duvi.services.account.model.enums.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;

    @NotNull
    @Length(min = 3, max = 20)
    private String title;
    @NotNull
    private String icon;

    @NotNull
    @Min(0)
    private BigDecimal amount;
    @NotNull
    private Category category;
    @NotNull
    private Currency currency;
    @NotNull
    private Frequency frequency;
    @NotNull
    private Type type;



    public Item(ItemDTO itemDTO) {
        this.title = itemDTO.title();
        this.icon = itemDTO.icon();
        this.amount = itemDTO.amount();
        this.category = itemDTO.category();
        this.currency = itemDTO.currency();
        this.frequency = itemDTO.frequency();
        this.type = itemDTO.type();
    }


}
