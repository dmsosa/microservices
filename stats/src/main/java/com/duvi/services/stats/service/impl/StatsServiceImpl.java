package com.duvi.services.stats.service.impl;

import com.duvi.services.stats.client.AccountClient;
import com.duvi.services.stats.domain.*;
import com.duvi.services.stats.domain.exception.EntityExistsException;
import com.duvi.services.stats.repository.ItemRepository;
import com.duvi.services.stats.repository.StatsRepository;
import com.duvi.services.stats.service.StatsService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Stream;

@Service
public class StatsServiceImpl implements StatsService {
    private StatsRepository statsRepository;
    private ItemRepository itemRepository;
    private AccountClient accountClient;
    public StatsServiceImpl(StatsRepository statsRepository,
                            ItemRepository itemRepository,
                            AccountClient accountClient) {
        this.statsRepository = statsRepository;
        this.itemRepository = itemRepository;
        this.accountClient = accountClient;
    }


    @Override
    public Flux<Stats> getStatsOfAccountByName(String accountName) {
        return Flux.fromStream(statsRepository.findAllByAccountName(accountName).stream());
    }

    @Override
    public Mono<Stats> createStats(AccountDTO account) {

        Boolean nullIncomes = account.getIncomes() == null || account.getIncomes().isEmpty();
        Boolean nullExpenses = account.getExpenses() == null || account.getExpenses().isEmpty();
        if (nullIncomes && nullExpenses) {
            throw new RuntimeException("This account have no items yet!");
        }
        //CREATE STATS
        Stats stats = new Stats();
        stats.setAccountName(account.getName());
        stats.setStatsDate(account.getLastSeen());
        List<Item> allItems = Stream.concat(account.getIncomes().stream(), account.getExpenses().stream()).toList();

        //Calculate total Incomes and total Expenses
        BigDecimal totalIncomes = calculateTotal(account.getIncomes());
        BigDecimal totalExpenses = calculateTotal(account.getExpenses());
        stats.setTotalIncomes(totalIncomes);
        stats.setTotalExpenses(totalExpenses);

        //Set stat to each item and save
        //Cascade Type is Merge, so while saving the items, the stats will be saved as well
        allItems.forEach(item -> item.setStats(stats));
        itemRepository.saveAll(allItems);

        return Mono.just(stats);
    }

    @Override
    public BigDecimal calculateTotal(List<Item> items) {
        BigDecimal total = BigDecimal.valueOf(0);
        for (Item i : items) {
            BigDecimal value = i.getAmount().multiply(i.getFrequency().getRepetition());
            total = total.add(value);
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

}
