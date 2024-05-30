package com.duvi.services.noti.domain.dto;

import com.duvi.services.noti.domain.enums.Frequency;
import com.duvi.services.noti.domain.enums.NotiType;

public record NotificationDTO (String accountName, String email, Frequency frequency, NotiType notiType) {
}
