package com.duvi.services.noti.service;

import com.duvi.services.noti.domain.NotiType;
import com.duvi.services.noti.domain.Recipient;
import com.duvi.services.noti.domain.dto.NotiSettingsDTO;
import org.springframework.stereotype.Service;

@Service
public interface NotiService {
    void sendRemindNoti();
    void sendBackupNoti();
}
