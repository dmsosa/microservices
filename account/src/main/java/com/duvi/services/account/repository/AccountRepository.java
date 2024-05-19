package com.duvi.services.account.repository;

import com.duvi.services.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    public boolean existsByName(String name);
    public Optional<Account> findByName(String name);
}
