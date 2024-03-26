package com.duvi.services.noti.repository;

import com.duvi.services.noti.domain.NotiSettings;
import com.duvi.services.noti.domain.NotiType;
import com.duvi.services.noti.domain.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotiRepository extends JpaRepository<NotiSettings, Long> {

    public Optional<NotiSettings> findByRecipientAndType(Recipient recipient, NotiType type);
    public List<NotiSettings> findAllByRecipient(Recipient recipient);
}
