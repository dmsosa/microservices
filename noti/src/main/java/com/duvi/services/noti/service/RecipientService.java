package com.duvi.services.noti.service;

import com.duvi.services.noti.domain.NotiSettings;
import com.duvi.services.noti.domain.NotiType;
import com.duvi.services.noti.domain.Recipient;
import com.duvi.services.noti.domain.dto.NotiSettingsDTO;

import java.util.List;

public interface RecipientService {

    Recipient findOrCreateRecipient(String accountName, String email);
    void removeNotification(Recipient recipient, NotiType notiType);
    void setNotification(Recipient recipient, NotiSettingsDTO notiSettings);
    List<Recipient> findReadyToNoti(NotiType notiType);
    void MarkNotified(Recipient recipient, NotiType notiType);

}
