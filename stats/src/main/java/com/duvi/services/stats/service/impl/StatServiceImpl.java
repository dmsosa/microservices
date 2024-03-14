package com.duvi.services.stats.service.impl;

import com.duvi.services.stats.domain.*;
import com.duvi.services.stats.repository.DatapointRepository;
import com.duvi.services.stats.repository.ItemRepository;
import com.duvi.services.stats.repository.StatRepository;
import com.duvi.services.stats.service.StatService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatServiceImpl implements StatService {
    private StatRepository statRepository;
    private ItemRepository itemRepository;
    private DatapointRepository datapointRepository;
    public StatServiceImpl(StatRepository statRepository,
                           ItemRepository itemRepository,
                           DatapointRepository datapointRepository) {
        this.statRepository = statRepository;
        this.itemRepository = itemRepository;
        this.datapointRepository = datapointRepository;
    }

    @Override
    public Datapoint getStatsOfAccount(String accountName) {
        //todo account client
        return datapointRepository.findAll().getFirst();
//        LocalDateTime = account.getLastSeen();
    }

    @Override
    public Datapoint saveChanges(Account account) {

        //CREATE DATAPOINT
        DatapointId datapointId = new DatapointId(account.getName(), LocalDateTime.now());
        Datapoint datapoint = new Datapoint();
        datapoint.setId(datapointId);
        datapointRepository.save(datapoint);

        //SAVE ALL INCOMES
        for (Item income : account.getIncomes()) {
            income.setDatapoint(datapoint);
            itemRepository.save(income);
        }
        //SAVE ALL EXPENSES
        for (Item expense : account.getExpenses()) {
            expense.setDatapoint(datapoint);
            itemRepository.save(expense);
        }

        //CREATE STAT FOR THE GIVEN DATAPOINT
        Stat stat = new Stat();
        stat.setDatapoint(datapoint);
        stat.setTotalIncomes(calculateTotal(account.getIncomes()));
        stat.setTotalExpenses(calculateTotal(account.getExpenses()));
        List<Item> savings = calculateSavings(account.getIncomes(), account.getExpenses());

        //SAVE SAVINGS
        for (Item saving : savings) {
            saving.setDatapoint(datapoint);
            itemRepository.save(saving);
        }

        stat.setTotalSavings(calculateCurrentSaving(datapoint, savings, account.getIncomes(), account.getExpenses()));


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
    public List<Item> calculateSavings(List<Item> incomes, List<Item> expenses) {
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

        return List.of(savingsDay, savingsMonth, savingsQuarter, savingsYear);

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
