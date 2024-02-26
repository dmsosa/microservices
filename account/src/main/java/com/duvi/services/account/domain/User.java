package com.duvi.services.account.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class User {
    @NotNull
    @Length(min = 3, max = 40)
    private String username;
    @NotNull
    @Length(min = 6, max = 40)
    private String password;
}
