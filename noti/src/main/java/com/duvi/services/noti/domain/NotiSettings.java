package com.duvi.services.noti.domain;

import com.duvi.services.noti.domain.dto.NotiSettingsDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notisettings")
@Getter
@Setter
public class NotiSettings {
    @EmbeddedId
    NotiSettingsId id;
    @ManyToOne
    @JoinColumn(name = "account_name", nullable = false)
    private Recipient recipient;
    private Boolean active;
    private Frequency frequency;
    private LocalDateTime lastNotified;
    private NotiType type;

    public NotiSettings(NotiSettingsDTO settingsDTO) {
        this.active = settingsDTO.active();
        this.frequency = settingsDTO.frequency();
        this.lastNotified = null;
        this.type = settingsDTO.type();
    }
}
