package com.duvi.services.account.repository;

import com.duvi.services.account.model.Account;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;


@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        Account a1 = new Account();
        Account a2 = new Account();
        Account a3 = new Account();
        a1.setName("test1");
        a1.setNote("Noting Everything!");
        a1.setLastSeen(LocalDateTime.now());
        a2.setName("test2");
        a2.setNote("Noting Something!");
        a2.setLastSeen(LocalDateTime.now().plusDays(2));
        a3.setName("test3");
        a3.setNote("Noting Nothing!");
        a3.setLastSeen(LocalDateTime.now().plusDays(3));
        entityManager.persist(a1);
        entityManager.persist(a2);
        entityManager.persist(a3);
    }

    @Test
    void testPopulatedDB() {
        List<Account> accountList = accountRepository.findAll();
        assertThat(accountList.size() > 0).isTrue();
    }
}