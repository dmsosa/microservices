package com.duvi.services.noti.repository;

import com.duvi.services.noti.domain.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, String> {
}
