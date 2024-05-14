package com.duvi.gateway.web;

import com.duvi.gateway.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.spring6.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;





@Controller
public class WebController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebClient.Builder webClientBuilder;
    public WebController(WebClient.Builder webClientBuilder, ReactiveClientRegistrationRepository clientRegistrationRepository) {
        this.webClientBuilder = webClientBuilder;
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

        //If account is null (is not created yet), then the gateway creates the new account
        accountMono = webClientBuilder
                    .build()
                    .get()
                    .uri("/account/" + username)
                    .retrieve()
                    .bodyToMono(Account.class)
                    .onErrorResume((e -> {
                        logger.info("Exception Catched: " + e.getMessage());
                        return webClientBuilder.build().post().uri("/account/create/" + username)
                                .retrieve().bodyToMono(Account.class);}));

        //Get statistics of account
        Flux<Stats> statsFlux = webClientBuilder.build().get().uri("/stats/" + username)
                .retrieve().bodyToFlux(Stats.class);
        IReactiveDataDriverContextVariable contextVariable = new ReactiveDataDriverContextVariable(statsFlux);
        model.addAttribute("account", accountMono);
        model.addAttribute("stats", contextVariable);
        return "index";
    }

    //CRUD Operations
    //Register user and create account

    @RequestMapping(method = {RequestMethod.POST}, value = "/edit/{accountName}")
    public String editAccount(@PathVariable String accountName, @ModelAttribute Account account, BindingResult bindingResult, Model model) {
        webClientBuilder.build()
                .post()
                .uri("http://gateway:8061/account/edit/" + accountName)
                .bodyValue(account)
                .retrieve()
                .toBodilessEntity()
                .subscribe( v -> v.getStatusCode());
        return "redirect:/index";
    }
    @RequestMapping(method = {RequestMethod.POST}, value = "/save")
    public String saveAccountStats(@ModelAttribute Account account, BindingResult bindingResult, Model model) {
        webClientBuilder.build()
                .post()
                .uri("http://gateway:8061/stats/" + account.getName())
                .bodyValue(account)
                .retrieve()
                .toBodilessEntity()
                .subscribe( v -> v.getStatusCode());
        return "redirect:/index";
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/save", params = {"addItem"})
    public String addItemRow(@ModelAttribute Account account, BindingResult bindingResult, Model model) {
        account.getItems().add(new Item());
        return "redirect:/index";
    }
    @RequestMapping(method = {RequestMethod.POST}, value = "/save", params = {"removeItem"})
    public String addItemRow(@RequestParam Long removeItemId, @ModelAttribute Account account, BindingResult bindingResult, Model model) {
        account.getItems().remove(removeItemId);
        return "redirect:/index";
    }
}
