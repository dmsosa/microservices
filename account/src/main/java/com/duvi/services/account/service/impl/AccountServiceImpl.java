package com.duvi.services.account.service.impl;

import com.duvi.services.account.client.AuthClient;
import com.duvi.services.account.client.StatsClient;
import com.duvi.services.account.model.*;
import com.duvi.services.account.model.dto.AccountDTO;
import com.duvi.services.account.model.dto.ItemDTO;
import com.duvi.services.account.model.enums.Type;
import com.duvi.services.account.model.exception.EntityNotFoundException;
import com.duvi.services.account.model.exception.EntityExistsException;
import com.duvi.services.account.repository.AccountRepository;
import com.duvi.services.account.service.AccountService;
import com.duvi.services.account.service.ItemService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private AuthClient authClient;
    private StatsClient statsClient;
    private ItemService itemService;
    public AccountServiceImpl(AccountRepository accountRepository,
                              AuthClient authClient,
                              StatsClient statsClient,
                              ItemService itemService) {
        this.authClient = authClient;
        this.statsClient = statsClient;
        this.accountRepository = accountRepository;
        this.itemService = itemService;
    }

    //the DTO organizes the incomes and expenses before sending them to the stats service or any other client
    @Override
    public AccountDTO createDTO(Account account) {
        List<ItemDTO> dtoList = itemService.createDTOForAll(account.getItems());
        List<ItemDTO> incomes = new ArrayList<ItemDTO>();
        List<ItemDTO> expenses = new ArrayList<ItemDTO>();
        for (ItemDTO item : dtoList) {
            if (item.type() == Type.INCOME) {
                incomes.add(item);
            } else if (item.type() == Type.EXPENSE) {
                expenses.add(item);
            } else {
                continue;
            }
        }
        return new AccountDTO(account.getName(), account.getLastSeen(), account.getNote(), account.getAvatar(), incomes, expenses, account.getCurrency());
    }
    @Override
    public AccountDTO createAccount(String accountName) throws EntityExistsException {
        if (accountRepository.existsByName(accountName)) {
            throw new EntityExistsException("Account with name: \"%s\" does not exists!".formatted(accountName));
        }

        Account account = new Account();
        account.setName(accountName);
        account.setLastSeen(LocalDateTime.now());
        account.setNote("I'm using microservices!");
        return createDTO(accountRepository.save(account));
    }

    @Override
    public AccountDTO editAccountDetails(String name, AccountDTO accountDTO) {

        //updating account
        Account accountToUpdate = accountRepository.findByName(name).get();

        accountToUpdate.setNote(accountDTO.note());
        accountToUpdate.setAvatar(accountDTO.avatar());

        return this.createDTO(accountRepository.save(accountToUpdate));
    }
    @Override
    public AccountDTO editItems(String name, AccountDTO accountDTO) {

        //updating account
        Account account = accountRepository.findByName(name).get();

        Set<ItemDTO> itemSet = Stream.concat(accountDTO.incomes().stream(), accountDTO.expenses().stream()).collect(Collectors.toSet());
        Set<Item> itemsToSave = itemService.editOrCreateAll(itemSet);

        itemService.compareAndDeleteItems(account, itemsToSave);

        account.setLastSeen(LocalDateTime.now());

        return this.createDTO(accountRepository.save(account));
    }

    @Override
    public AccountDTO getAccountByName(String name) throws EntityNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findByName(name);
        if (optionalAccount.isEmpty()) {
            throw new EntityNotFoundException("Account with name: \"%s\" does not exists!".formatted(name));
        }
        return this.createDTO(optionalAccount.get());
    }

    @Override
    public void deleteAccountByName(String name) throws EntityNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findByName(name);
        if (optionalAccount.isEmpty()) {
            throw new EntityNotFoundException("Account with name: \"%s\" does not exists!".formatted(name));
        }
        authClient.deleteUser(name);
        accountRepository.delete(optionalAccount.get());
    }

    @Override
    public void saveChanges(AccountDTO account) {

        //todo accountDTO + itemDTO
        statsClient.saveAccountStats(account);
    }
}
