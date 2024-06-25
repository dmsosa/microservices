package com.duvi.services.stats.service.impl;

import com.duvi.services.stats.client.AccountClient;
import com.duvi.services.stats.domain.*;
import com.duvi.services.stats.domain.dto.AccountDTO;
import com.duvi.services.stats.domain.dto.ItemDTO;
import com.duvi.services.stats.domain.dto.StatsDTO;
import com.duvi.services.stats.domain.exception.EntityExistsException;
import com.duvi.services.stats.repository.ItemRepository;
import com.duvi.services.stats.repository.StatsRepository;
import com.duvi.services.stats.service.StatsService;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
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
    public StatsDTO createDTO(Stats stats) {
        return new StatsDTO(
                stats.getId(),
                stats.getAccountName(),
                stats.getStatsDate(),
                stats.getTotalIncomes(),
                stats.getTotalExpenses()
        );
    }
    @Override
    public Flux<StatsDTO> getStatsOfAccountByName(String accountName) {
        List<Stats> statsList = statsRepository.findAllByAccountNameOrderByStatsDateDesc(accountName);
        Stream<StatsDTO> dtoStream = statsList.stream().map(this::createDTO);
        return Flux.fromStream(dtoStream);
    }

    @Override
    public Mono<Void> createStats(AccountDTO account) throws EntityExistsException {

        Assert.notNull(account.getIncomes(), "Creating stats: Account's incomes can not be null");
        Assert.notNull(account.getExpenses(), "Creating stats: Account's expenses can not be null");
        Assert.notEmpty(account.getIncomes(), "Creating stats: Account's incomes can not be empty");
        Assert.notEmpty(account.getExpenses(), "Creating stats: Account's expenses can not be empty");
        return Mono.fromRunnable(() -> {
            //CREATE STATS
            Stats stats = new Stats();
            stats.setAccountName(account.getName());
            stats.setStatsDate(account.getLastSeen());
            stats.setCurrency(account.getCurrency());

            List<ItemDTO> allItems = Stream.concat(account.getIncomes().stream(), account.getExpenses().stream()).toList();

            //Calculate total Incomes and total Expenses
            BigDecimal totalIncomes = calculateTotal(account.getIncomes());
            BigDecimal totalExpenses = calculateTotal(account.getExpenses());
            stats.setTotalIncomes(totalIncomes);
            stats.setTotalExpenses(totalExpenses);

            //Create new item if it does not exist
            //Set stat to each item and save
            //Cascade Type is Merge, so when items are saved, so are the stats
            Stats savedStats = statsRepository.save(stats);

            allItems.forEach(itemDTO -> {
                Item item;
                item = new Item();
                item.setTitle(itemDTO.title());
                item.setAmount(itemDTO.amount());
                item.setCategory(itemDTO.category());
                item.setCurrency(itemDTO.currency());
                item.setFrequency(itemDTO.frequency());
                item.setType(itemDTO.type());
                item.setStats(savedStats);
                item.setStatsDate(savedStats.getStatsDate());
                itemRepository.save(item);
            });
        });

    }

    @Override
    public BigDecimal calculateTotal(List<ItemDTO> items) {
        BigDecimal total = BigDecimal.valueOf(0);
        for (ItemDTO i : items) {
            BigDecimal value = i.amount().multiply(i.frequency().getRepetition());
            total = total.add(value);
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

}
