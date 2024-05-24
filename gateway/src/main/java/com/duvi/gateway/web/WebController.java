package com.duvi.gateway.web;

import com.duvi.gateway.model.*;
import com.duvi.gateway.model.enums.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.oidc.session.InMemoryOidcSessionRegistry;
import org.springframework.security.oauth2.client.oidc.session.OidcSessionRegistry;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.adapter.DefaultServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;


@Controller
public class WebController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebClient.Builder webClientBuilder;
    public WebController(WebClient.Builder webClientBuilder, ReactiveClientRegistrationRepository clientRegistrationRepository) {
        this.webClientBuilder = webClientBuilder;
    }


    @ModelAttribute(name = "allAccountIcons")
    public List<AccountIcon> allAccountIcons() {
        return Arrays.asList(AccountIcon.ALL);
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
        if (model.getAttribute("account") != null) {
            return "index";
        }
        //If account is null, then the gateway gets it or creates a new account
        accountMono = webClientBuilder
                .build()
                .get()
                .uri("/account/" + username)
                .retrieve()
                .bodyToMono(AccountDTO.class)
                .switchIfEmpty( webClientBuilder.build()
                        .post()
                        .uri("/account/create/" + username)
                        .retrieve().bodyToMono(AccountDTO.class))
                .onErrorResume((e -> {
                    logger.info("Exception Catched: " + e.getMessage());
                    return webClientBuilder
                            .build()
                            .get()
                            .uri("/account/" + username)
                            .retrieve()
                            .bodyToMono(AccountDTO.class)
                            .timeout(Duration.ofSeconds(5));}));

        //Get statistics of account
        model.addAttribute("account", accountMono);

        return "index";
    }


    //CRUD Operations
    //Register user and create account

    @RequestMapping(method = {RequestMethod.POST}, value = "/edit/{accountName}")
    public String editAccount(@PathVariable String accountName, @ModelAttribute(name = "account") AccountDTO account, BindingResult bindingResult, Model model) {
        Mono<AccountDTO> updatedAccount = webClientBuilder.build()
                .post()
                .uri("/account/edit/" + accountName)
                .bodyValue(account)
                .retrieve()
                .bodyToMono(AccountDTO.class);
        model.addAttribute("account", updatedAccount);
        return "index";
    }
    @RequestMapping(value = "/editItems/{accountName}")
    public String editAccountItems(@PathVariable String accountName,
                                   @ModelAttribute(name = "account") AccountDTO account,
                                   BindingResult bindingResult,
                                   Model model) {
        Mono<AccountDTO> updatedAccount = webClientBuilder.build()
                .post()
                .uri("/account/items/" + accountName)
                .bodyValue(account)
                .retrieve()
                .bodyToMono(AccountDTO.class);
        model.addAttribute("account", updatedAccount);
        return "index";
    }

    @RequestMapping(value = "/editItems/{accountName}", params = {"addIncome"})
    public String addIncomeRow(@ModelAttribute AccountDTO account, BindingResult bindingResult, Model model) {
        account.getIncomes().add(new ItemDTO());
        model.addAttribute("account", account);
        return "index";
    }
    @RequestMapping(value = "/editItems/{accountName}", params = {"removeIncome"})
    public String removeIncomeRow(@RequestParam Integer removeIncome, @ModelAttribute AccountDTO account, BindingResult bindingResult, Model model) {
        ItemDTO toRemove = account.getIncomes().get(removeIncome);
        account.getIncomes().remove(toRemove);
        model.addAttribute("account", account);
        return "index";
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/save")
    public String saveAccountStats(@ModelAttribute AccountDTO accountDTO, BindingResult bindingResult, Model model) {
        webClientBuilder.build()
                .post()
                .uri("/stats/" + accountDTO.getName())
                .bodyValue(accountDTO)
                .retrieve()
                .toBodilessEntity()
                .subscribe( v -> v.getStatusCode());
        return "redirect:/index";
    }
}
