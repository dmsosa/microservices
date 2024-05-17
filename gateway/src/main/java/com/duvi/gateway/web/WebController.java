package com.duvi.gateway.web;

import com.duvi.gateway.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.cloud.gateway.filter.WeightCalculatorWebFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;

import org.springframework.security.oauth2.client.web.server.OAuth2AuthorizationCodeGrantWebFilter;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.server.authorization.AuthorizationWebFilter;
import org.springframework.security.web.server.authorization.ExceptionTranslationWebFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.thymeleaf.spring6.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
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


    @ModelAttribute(name = "iconNames")
    public List<String> loadIconNames() {
        String[] iconNames = {"piggy", "bear", "fox", "boss", "cow", "secretary", "horse", "rabbit", "elephant", "turtle"};
        return Arrays.asList(iconNames);
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
        Mono<Account> accountMono;
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
                .bodyToMono(Account.class)
                .switchIfEmpty( webClientBuilder.build()
                        .post()
                        .uri("/account/create/" + username)
                        .retrieve().bodyToMono(Account.class))
                .onErrorResume((e -> {
                    logger.info("Exception Catched: " + e.getMessage());
                    return webClientBuilder
                            .build()
                            .get()
                            .uri("/account/" + username)
                            .retrieve()
                            .bodyToMono(Account.class)
                            .timeout(Duration.ofSeconds(3));}));

        //Get statistics of account
        model.addAttribute("account", accountMono);
        return "index";
    }


    //CRUD Operations
    //Register user and create account

    @RequestMapping(method = {RequestMethod.POST}, value = "/edit/{accountName}")
    public String editAccount(@PathVariable String accountName, @ModelAttribute Account account, BindingResult bindingResult, Model model) {
        Mono<Account> updatedAccount = webClientBuilder.build()
                .post()
                .uri("/account/edit/" + accountName)
                .bodyValue(BodyInserters.fromValue(account))
                .retrieve()
                .bodyToMono(Account.class)
                .doOnError(error -> error.printStackTrace());
        model.addAttribute("account", updatedAccount);
    return "redirect:/index";
    }
    @RequestMapping(method = {RequestMethod.POST}, value = "/save")
    public String saveAccountStats(@ModelAttribute Account account, BindingResult bindingResult, Model model) {
        webClientBuilder.build()
                .post()
                .uri("/stats/" + account.getName())
                .bodyValue(account)
                .retrieve()
                .toBodilessEntity()
                .subscribe( v -> v.getStatusCode());
        return "redirect:/index";
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/save", params = {"addIncome"})
    public String addIncomeRow(@ModelAttribute Account account, BindingResult bindingResult, Model model) {
        account.getIncomes().add(new Item());
        return "redirect:/index";
    }
    @RequestMapping(method = {RequestMethod.POST}, value = "/save", params = {"removeIncomeId"})
    public String removeIncomeRow(@RequestParam Long removeIncomeId, @ModelAttribute Account account, BindingResult bindingResult, Model model) {
        account.getIncomes().remove(removeIncomeId);
        return "redirect:/index";
    }
}
