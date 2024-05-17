package com.duvi.services.account.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class User {

    private Long id;

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
