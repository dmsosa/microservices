package com.duvi.services.noti.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notisettings")
@Getter
@Setter
public class NotiSettings {
    @ManyToOne
    @JoinColumn(name = "account_name", nullable = false)
    private Recipient recipient;
    private Boolean active;
    private Frequency frequency;
    private LocalDateTime lastNotified;
    private NotiType type;
}
