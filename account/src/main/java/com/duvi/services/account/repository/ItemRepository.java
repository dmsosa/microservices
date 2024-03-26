package com.duvi.services.account.repository;

import com.duvi.services.account.domain.Account;
import com.duvi.services.account.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    public List<Item> findByAccount(Account account);
}
