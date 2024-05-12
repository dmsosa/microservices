package com.duvi.services.stats.service;

import com.duvi.services.stats.domain.AccountDTO;
import com.duvi.services.stats.domain.Datapoint;
import com.duvi.services.stats.domain.Item;
import com.duvi.services.stats.domain.exception.EntityExistsException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface StatService {
    List<Datapoint> getStatsOfAccountByName(String accountName);
    Datapoint saveChanges(AccountDTO account) throws EntityExistsException;
    BigDecimal calculateTotal(List<Item> items);
    List<Item> calculateSavings(Datapoint datapoint, List<Item> incomes, List<Item> expenses);
    BigDecimal calculateCurrentSaving(Datapoint datapoint, List<Item> savings, List<Item> incomes, List<Item> expenses);
}
