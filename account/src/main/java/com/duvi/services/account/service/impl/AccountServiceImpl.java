package com.duvi.services.account.service.impl;

import com.duvi.services.account.client.AuthClient;
import com.duvi.services.account.client.StatClient;
import com.duvi.services.account.model.*;
import com.duvi.services.account.model.dto.AccountDTO;
import com.duvi.services.account.model.dto.ItemDTO;
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
    private StatClient statClient;
    private ItemService itemService;
    public AccountServiceImpl(AccountRepository accountRepository,
                              AuthClient authClient,
                              StatClient statClient,
                              ItemService itemService) {
        this.authClient = authClient;
        this.statClient = statClient;
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
        return new AccountDTO(account.getName(), account.getLastSeen(), account.getNote(), account.getIcon(), incomes, expenses, account.getCurrency());
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

        //updating user
        User userToUpdate = authClient.getUser(name);
        userToUpdate.setUsername(accountDTO.name());
        authClient.editUser(name, userToUpdate);

        //updating account
        Account accountToUpdate = accountRepository.findByName(name).get();

        accountToUpdate.setName(accountDTO.name());
        accountToUpdate.setNote(accountDTO.note());
        accountToUpdate.setIcon(accountDTO.icon());

        return this.createDTO(accountRepository.save(accountToUpdate));
    }
    @Override
    public AccountDTO editItems(String name, AccountDTO accountDTO) {

        //updating account
        Account account = accountRepository.findByName(name).get();

        Set<ItemDTO> itemSet = Stream.concat(accountDTO.incomes().stream(), accountDTO.expenses().stream()).collect(Collectors.toSet());
        Set<Item> items = itemService.editOrCreateAll(itemSet);
        account.setItems(items);
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
        statClient.saveAccount(account);
    }
}
