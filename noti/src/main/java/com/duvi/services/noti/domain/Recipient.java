package com.duvi.services.noti.domain;

import com.duvi.services.noti.domain.enums.Frequency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Recipient {
    private String accountName;
    private String email;
    private String frequencyValue;
}
