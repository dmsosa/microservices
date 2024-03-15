package com.duvi.services.stats.service;

import com.duvi.services.stats.domain.Account;
import com.duvi.services.stats.domain.Datapoint;
import com.duvi.services.stats.domain.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface StatService {
    Datapoint getStatsOfAccount(Account account);
    Datapoint getStatsOfAccountByName(String accountName);
    Datapoint saveChanges(Account account);
    BigDecimal calculateTotal(List<Item> items);
    List<Item> calculateSavings(Datapoint datapoint, List<Item> incomes, List<Item> expenses);
    BigDecimal calculateCurrentSaving(Datapoint datapoint, List<Item> savings, List<Item> incomes, List<Item> expenses);
}
