package com.duvi.services.stats.service.impl;

import com.duvi.services.stats.client.AccountClient;
import com.duvi.services.stats.domain.*;
import com.duvi.services.stats.domain.exception.EntityExistsException;
import com.duvi.services.stats.repository.DatapointRepository;
import com.duvi.services.stats.repository.ItemRepository;
import com.duvi.services.stats.repository.StatRepository;
import com.duvi.services.stats.service.StatService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StatServiceImpl implements StatService {
    private StatRepository statRepository;
    private ItemRepository itemRepository;
    private DatapointRepository datapointRepository;
    private AccountClient accountClient;
    public StatServiceImpl(StatRepository statRepository,
                           ItemRepository itemRepository,
                           DatapointRepository datapointRepository,
                           AccountClient accountClient) {
        this.statRepository = statRepository;
        this.itemRepository = itemRepository;
        this.datapointRepository = datapointRepository;
        this.accountClient = accountClient;
    }


    @Override
    public List<Datapoint> getStatsOfAccountByName(String accountName) {
        DatapointId id = new DatapointId();
        id.setAccountName(accountName);
        Datapoint exampleDatapoint = new Datapoint();
        exampleDatapoint.setId(id);
        List<Datapoint> datapoints =  datapointRepository.findAll(Example.of(exampleDatapoint));
        return datapoints;
    }

    @Override
    public Datapoint saveChanges(AccountDTO account) throws EntityExistsException {

        //CREATE DATAPOINT
        DatapointId datapointId = new DatapointId(account.getName(), account.getLastSeen());
        Datapoint datapoint = new Datapoint();
        datapoint.setId(datapointId);
        if (datapointRepository.existsById(datapointId)) {
            throw new EntityExistsException("Datapoint for account %1$s at date: %2$s already saved!".formatted(datapointId.getAccountName(), datapointId.getDataDate()));
        }
        datapoint = datapointRepository.save(datapoint);

        List<Item> incomes = new ArrayList<Item>();
        List<Item> expenses = new ArrayList<Item>();

        if (account.getItems() == null) {
            throw new RuntimeException("This account have no items yet!");
        }
        //SAVE ALL ITEMS, separate incomes from expenses
        for (ItemDTO itemDTO : account.getItems()) {
            //Item von DTO erstellen, damit unsere Id nicht uberschrieben ist
            Item item = new Item(itemDTO);
            item.setDatapoint(datapoint);
            Item savedItem = itemRepository.save(item);
            switch (savedItem.getType()) {
                case INCOME -> { incomes.add(savedItem); }
                case EXPENSE -> { expenses.add(savedItem); }
            }
        }

        //CREATE STAT FOR THE GIVEN DATAPOINT
        Stat stat = new Stat();
        stat.setDatapoint(datapoint);
        stat.setTotalIncomes(calculateTotal(incomes));
        stat.setTotalExpenses(calculateTotal(expenses));
        List<Item> savings = calculateSavings(datapoint, incomes, expenses);



        stat.setTotalSavings(calculateCurrentSaving(datapoint, savings, incomes, expenses));


        statRepository.save(stat);

        return datapointRepository.getReferenceById(datapointId);

    }

    @Override
    public BigDecimal calculateTotal(List<Item> items) {
        BigDecimal total = BigDecimal.valueOf(0);
        for (Item i : items) {
            total = i.getAmount().add(total);
        }
        return total;
    }

    @Override
    public List<Item> calculateSavings(Datapoint datapoint, List<Item> incomes, List<Item> expenses) {
        Item savingsDay = new Item("DAILY SAVINGS", Category.FIXED, Currency.getDefault(), Frequency.DAY, ItemType.SAVING);
        Item savingsMonth = new Item("MONTHLY SAVINGS", Category.FIXED, Currency.getDefault(), Frequency.MONTH, ItemType.SAVING);
        Item savingsQuarter = new Item("QUARTERLY SAVINGS", Category.FIXED, Currency.getDefault(), Frequency.QUARTER, ItemType.SAVING);
        Item savingsYear = new Item("YEARLY SAVINGS", Category.FIXED, Currency.getDefault(), Frequency.YEAR, ItemType.SAVING);

        BigDecimal totalSavingsDay = BigDecimal.ZERO;
        BigDecimal totalSavingsMonth = BigDecimal.ZERO;
        BigDecimal totalSavingsQuarter = BigDecimal.ZERO;
        BigDecimal totalSavingsYear = BigDecimal.ZERO;

        //Calculate fixed savings, without calculating occasional incomes or expenses
        for (Item income : incomes) {
            if (income.getCategory().equals(Category.OCCASIONAL)) { continue; }
            switch (income.getFrequency()) {
                case DAY -> {
                    totalSavingsDay = totalSavingsDay.add(income.getAmount());
                }
                case MONTH -> {
                    totalSavingsMonth = totalSavingsMonth.add(income.getAmount());
                }
                case QUARTER -> {
                    totalSavingsQuarter = totalSavingsQuarter.add(income.getAmount());
                }
                case YEAR -> {
                    totalSavingsYear = totalSavingsYear.add(income.getAmount());
                }
            }
        }

        for (Item expense : expenses) {
            if (expense.getCategory().equals(Category.OCCASIONAL)) { continue; }
            switch (expense.getFrequency()) {
                case DAY -> {
                    totalSavingsDay = totalSavingsDay.subtract(expense.getAmount());
                }
                case MONTH -> {
                    totalSavingsMonth = totalSavingsMonth.subtract(expense.getAmount());
                }
                case QUARTER -> {
                    totalSavingsQuarter = totalSavingsQuarter.subtract(expense.getAmount());
                }
                case YEAR -> {
                    totalSavingsYear = totalSavingsYear.subtract(expense.getAmount());
                }
            }
        }


        savingsDay.setAmount(totalSavingsDay);

        savingsMonth.setAmount(totalSavingsMonth.add(
                totalSavingsDay.multiply(Frequency.MONTH.getBaseRatio())
        ));

        savingsQuarter.setAmount(
                totalSavingsQuarter.add(
                        totalSavingsMonth.multiply(BigDecimal.valueOf(4))
                ).add(
                        totalSavingsDay.multiply(Frequency.QUARTER.getBaseRatio())
                )
        );

        savingsYear.setAmount(
                totalSavingsYear.add(
                        totalSavingsQuarter.multiply(totalSavingsQuarter.multiply(BigDecimal.valueOf(4)))
                ).add(
                        totalSavingsMonth.multiply(totalSavingsMonth.multiply(BigDecimal.valueOf(12)))
                ).add(
                        totalSavingsDay.multiply(Frequency.YEAR.getBaseRatio())
                )
        );

        List<Item> savings =  List.of(savingsDay, savingsMonth, savingsQuarter, savingsYear);
        //SAVE SAVINGS
        for (Item saving : savings) {
            saving.setDatapoint(datapoint);
            itemRepository.save(saving);
        };
        return savings;
    }

    @Override
    public BigDecimal calculateCurrentSaving(Datapoint datapoint, List<Item> savings, List<Item> incomes, List<Item> expenses) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime statsDate = datapoint.getId().getDataDate();
        Integer days = currentDate.getDayOfYear() - statsDate.getDayOfYear();
        Integer months = days.intValue() / 30;
        Integer quarters = days.intValue() / 90;
        Integer years = days.intValue() / 365;

        BigDecimal totalSavedDays = BigDecimal.ZERO;
        BigDecimal totalSavedMonths = BigDecimal.ZERO;
        BigDecimal totalSavedQuarters = BigDecimal.ZERO;
        BigDecimal totalSavedYears = BigDecimal.ZERO;
        BigDecimal occasionalIncomes = BigDecimal.ZERO;
        BigDecimal occasionalExpenses = BigDecimal.ZERO;

        for ( Item saving : savings ) {
            switch (saving.getFrequency()) {
                case DAY -> {
                    totalSavedDays = totalSavedDays.add(
                            saving.getAmount().multiply(BigDecimal.valueOf(days))
                    );
                }
                case MONTH -> {
                    totalSavedMonths = totalSavedMonths.add(
                            saving.getAmount().multiply(BigDecimal.valueOf(months))
                    );
                }
                case QUARTER -> {
                    totalSavedQuarters = totalSavedQuarters.add(
                            saving.getAmount().multiply(BigDecimal.valueOf(quarters))
                    );
                }
                case YEAR -> {
                    totalSavedYears = totalSavedYears.add(
                            saving.getAmount().multiply(BigDecimal.valueOf(years))
                    );
                    }
                }
            }

        for ( Item income : incomes ) {
            if (income.getCategory().equals(Category.FIXED)) { continue; }
            occasionalIncomes = occasionalIncomes.add(income.getAmount());
        }

        for ( Item expense : expenses ) {
            if (expense.getCategory().equals(Category.FIXED)) { continue; }
            occasionalExpenses = occasionalExpenses.add(expense.getAmount());
        }

        BigDecimal totalSavings = totalSavedDays
                        .add(totalSavedMonths)
                        .add(totalSavedQuarters)
                        .add(totalSavedYears)
                        .add(occasionalIncomes)
                        .subtract(occasionalExpenses);

        return totalSavings;
        }
}
