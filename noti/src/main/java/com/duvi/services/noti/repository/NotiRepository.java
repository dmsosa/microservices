package com.duvi.services.noti.repository;

import com.duvi.services.noti.domain.NotiSettings;
import com.duvi.services.noti.domain.NotiSettingsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotiRepository extends JpaRepository<NotiSettings, NotiSettingsId> {

}
