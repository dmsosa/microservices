package com.duvi.services.stats.repository;

import com.duvi.services.stats.domain.*;
import com.duvi.services.stats.domain.Currency;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@DataJpaTest
public class RepositoryTest {

    private final Logger logger = LoggerFactory.getLogger(RepositoryTest.class);
    @Autowired
    private DatapointRepository datapointRepository;
    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void populateDb() {
        Datapoint d1 = new Datapoint();
        Datapoint d2 = new Datapoint();
        Datapoint d3 = new Datapoint();
        Datapoint d4 = new Datapoint();
        fillWithData(d1, d2, d3, d4);
        entityManager.persist(d1);
        entityManager.persist(d2);
        entityManager.persist(d3);
        entityManager.persist(d4);
        entityManager.persist(d1.getStats());
        entityManager.persist(d2.getStats());
        entityManager.persist(d3.getStats());
        entityManager.persist(d4.getStats());
    }

    private static void fillWithData(Datapoint... datapoints) {
        ///////////////////////////////
        List<String> randNames = new ArrayList<String>();
        randNames.add("demo");randNames.add("edson");randNames.add("edmon");randNames.add("ursula");
        ///////////////////////////////
        List<Item> randItems = new ArrayList<Item>();
        Item exampleIncome = new Item("Some Income", BigDecimal.valueOf(1000), Category.FIXED, Currency.USD, Frequency.MONTH, ItemType.INCOME);
        Item exampleExpense = new Item("Some Expense", BigDecimal.valueOf(420), Category.FIXED, Currency.USD, Frequency.MONTH, ItemType.EXPENSE);
        randItems.add(exampleIncome); randItems.add(exampleExpense);
        ///////////////////////////////
        Stat randStats = new Stat();
        randStats.setTotalIncomes(BigDecimal.valueOf(1000));
        randStats.setTotalExpenses(BigDecimal.valueOf(420));
        randStats.setTotalSavings(BigDecimal.valueOf(580));

        List<Datapoint> datapointList = Arrays.stream(datapoints).toList();
        for (Integer i = 0 ; i < datapointList.size() ; i++) {
            DatapointId randId = new DatapointId(randNames.get(i), LocalDateTime.now().plusDays(i));
            Datapoint d = datapointList.get(i);
            d.setId(randId);
            d.setItems(new HashSet<>(randItems));
            randStats.setDatapoint(d);
            d.setStats(randStats);
        }
    }

    @Test
    void testDataPopulated() {
        List<Datapoint> datapointList = datapointRepository.findAll();
        assertThat(datapointList.size() > 0).isTrue();
    }

    @Test
    @DisplayName(value = "test GET operation")
    void whenDatapointExists_thenReturnObject() {
        DatapointId id = new DatapointId();
        id.setAccountName("edmon");
        Datapoint exampleDatapoint = new Datapoint();
        exampleDatapoint.setId(id);
        List<Datapoint> datapoint = datapointRepository.findAll(Example.of(exampleDatapoint));
        assertThat(datapoint.getFirst().getId().getAccountName().equals("edmon")).isTrue();
    }
}
