package com.duvi.services.noti.domain.dto;

import com.duvi.services.noti.domain.Frequency;
import com.duvi.services.noti.domain.NotiType;

import java.time.LocalDateTime;

public record NotiSettingsDTO(Boolean active, Frequency frequency, NotiType type) {
}
