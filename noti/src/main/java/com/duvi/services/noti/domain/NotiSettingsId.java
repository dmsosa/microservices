package com.duvi.services.noti.domain;

import com.duvi.services.noti.domain.NotiSettings;
import com.duvi.services.noti.domain.NotiType;
import com.duvi.services.noti.domain.Recipient;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class NotiSettingsId implements Serializable {

    String accountName;

    NotiType type;
    public NotiSettingsId(Recipient recipient, NotiType type) {
        this.accountName = recipient.getAccountName();
        this.type = type;
    }
}
