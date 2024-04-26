package com.duvi.services.account.domain.dto;

import com.duvi.services.account.domain.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountContextVarDTO {
    private Account account;
    private DatapointDTO datapoint;
}
