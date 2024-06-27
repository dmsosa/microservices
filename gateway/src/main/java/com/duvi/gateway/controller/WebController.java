package com.duvi.gateway.controller;

import com.duvi.gateway.model.*;
import com.duvi.gateway.model.enums.*;
import com.duvi.gateway.service.AccountService;
import com.duvi.gateway.service.StatsService;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import org.thymeleaf.spring6.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerResponse;

import java.net.URI;
import java.security.Principal;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Controller
public class WebController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private AccountService accountService;
    private StatsService statsService;
    private ReactiveOAuth2AuthorizedClientService authorizedClientService;

    public WebController(AccountService accountService, StatsService statsService, ReactiveOAuth2AuthorizedClientService authorizedClientService) {
        this.accountService = accountService;
        this.statsService = statsService;
        this.authorizedClientService = authorizedClientService;
    }


    @ModelAttribute(name = "allAccountAvatar")
    public List<AccountAvatar> allAccountIcons() {
        return Arrays.asList(AccountAvatar.ALL);
    }
    @ModelAttribute(name = "allItemIcons")
    public List<ItemIcon> allItemIcons() {
        return Arrays.asList(ItemIcon.ALL);
    }
    @ModelAttribute(name = "allCategories")
    public List<Category> loadCategories() {
        return Arrays.asList(Category.ALL);
    }
    @ModelAttribute(name = "allCurrencies")
    public List<Currency> loadCurrencies() {
        return Arrays.asList(Currency.ALL);
    }
    @ModelAttribute(name = "allFrequencies")
    public List<Frequency> loadFrequencies() {
        return Arrays.asList(Frequency.ALL);
    }
    //Authentication endpoints **Login, Register and Redirect to AuthServer

    @RequestMapping(method = {RequestMethod.GET}, value = "/demo")
    public String showDemoPage(
            Model model
    ) {

        Mono<AccountDTO> accountDTO = accountService.getAccount("demo");
        model.addAttribute("account", accountDTO);
        model.addAttribute("logged", false);
        return "index";
    }
    @RequestMapping(method = {RequestMethod.GET}, value = "/index")
    public String showIndexPage(
            @RegisteredOAuth2AuthorizedClient(registrationId = "gatewayClient")
            OAuth2AuthorizedClient gatewayClient,
            Authentication authentication,
            Model model) {

        //Initializing account Mono, we define it later.
        // Because it is necessary to check if the account already exists

        //Getting username from the resource owner's authentication
        Mono<AccountDTO> accountMono;
        DefaultOidcUser user = (DefaultOidcUser) authentication.getPrincipal();
        String username = user.getName();

        //If account is null, then the gateway gets it or creates a new account

        if (model.getAttribute("account") == null) {
            accountMono = accountService.getAccount(username);
            model.addAttribute("account", accountMono);
            //Get stats of account or null if empty
            Flux<StatsDTO> statsDTOFlux = statsService.getStatsOfAccount(username);
            IReactiveDataDriverContextVariable statsVariable = new ReactiveDataDriverContextVariable(statsDTOFlux);

            model.addAttribute("stats", statsVariable);
            model.addAttribute("logged", false);
        }

        return "index";
    }


    //CRUD Operations
    //Register user and create account

    @RequestMapping(method = {RequestMethod.POST}, value = "/edit/{accountName}")
    public String editAccount(@PathVariable String accountName, @ModelAttribute(name = "account") AccountDTO account, BindingResult bindingResult, Model model) {
        Mono<AccountDTO> updatedAccount = accountService.editAccount(accountName, account);
        model.addAttribute("account", updatedAccount);
        model.addAttribute("logged", true);
        return "index";
    }
    @RequestMapping(method = {RequestMethod.POST}, value = "/save/{accountName}")
    public String saveAccountChanges(@PathVariable String accountName,
                                   @ModelAttribute(name = "account") AccountDTO account,
                                   BindingResult bindingResult,
                                   Model model) {




        Mono<AccountDTO> updatedAccount = accountService.editAccountItems(accountName, account).flatMap(
                accountDTO -> {
                    statsService.saveStatsOfAccount(accountDTO);
                    return Mono.just(accountDTO);
                }
        );

        Flux<StatsDTO> statsDTOFlux = statsService.getStatsOfAccount(accountName).delayElements(Duration.ofMillis(5000));

        IReactiveDataDriverContextVariable statsVariable = new ReactiveDataDriverContextVariable(statsDTOFlux);

        model.addAttribute("account", updatedAccount);
        model.addAttribute("stats", statsVariable);
        model.addAttribute("logged", true);

        return "index";
    }
}
