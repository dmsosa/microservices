package com.duvi.services.account.repository;

import com.duvi.services.account.model.Account;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;


@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;


    @Test
    void whenFindAll_DbIsPopulated() {
        List<Account> accountList = accountRepository.findAll();
        assertThat(!accountList.isEmpty()).isTrue();
    }
}