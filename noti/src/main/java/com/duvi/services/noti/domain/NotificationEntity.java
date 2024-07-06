package com.duvi.services.noti.domain;

import com.duvi.services.noti.domain.dto.NotificationDTO;
import com.duvi.services.noti.domain.enums.Frequency;
import com.duvi.services.noti.domain.enums.NotiType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountName;
    private String email;
    private Frequency frequency;
    @Enumerated(EnumType.STRING)
    private NotiType notiType;
    private Boolean active;
    private LocalDate lastNotified;

    public NotificationEntity(String accountName, String email, Frequency frequency, NotiType notiType) {
        this.accountName = accountName;
        this.email = email;
        this.frequency = frequency;
        this.notiType = notiType;
        this.active = true;
        this.lastNotified = LocalDate.now();
    }
    public NotificationEntity(NotificationDTO dto) {
        this.accountName = dto.accountName();
        this.email = dto.email();
        this.frequency = dto.frequency();
        this.notiType = dto.notiType();
        this.active = true;
        this.lastNotified = LocalDate.now();
    }

    public Boolean isActive() {
        return active;
    }
}
