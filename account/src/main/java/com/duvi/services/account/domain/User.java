package com.duvi.services.account.domain;

import jakarta.validation.constraints.Email;
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
    @Email
    private String email;
    @NotNull
    @Length(min = 6, max = 40)
    private String password;
}
