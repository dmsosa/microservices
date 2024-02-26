package com.duvi.services.account.repository;

import com.duvi.services.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    public boolean existsByName(String name);
}
