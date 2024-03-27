package com.duvi.services.account.repository;

import com.duvi.services.account.domain.Account;
import com.duvi.services.account.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    public List<Item> findByAccount(Account account);
    public Optional<Item> findByTitle(String title);
}
