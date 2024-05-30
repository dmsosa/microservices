package com.duvi.services.noti.repository;

import com.duvi.services.noti.domain.NotificationEntity;
import com.duvi.services.noti.domain.enums.NotiType;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotiRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findAllByAccountName(String accountName);
    NotificationEntity deleteByAccountNameAndNotiType(String accountName, NotiType notiType);
    @Query(value = "SELECT * FROM notifications WHERE noti_type = 'REMIND' AND active = true AND last_notified < CURRENT_DATE - frequency", nativeQuery = true)
    List<NotificationEntity> findAllRemindReady();

    @Query(value = "SELECT * FROM notifications WHERE noti_type = 'BACKUP' AND active = true AND last_notified < CURRENT_DATE - frequency", nativeQuery = true)
    List<NotificationEntity> findAllBackupReady();

}
