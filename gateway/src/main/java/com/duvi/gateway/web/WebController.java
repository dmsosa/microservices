package com.duvi.gateway.web;

import com.duvi.gateway.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
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
import org.springframework.web.server.ServerWebExchange;
import org.thymeleaf.spring6.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;



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
        Mono<Account> accountMono;
        DefaultOidcUser user = (DefaultOidcUser) authentication.getPrincipal();
        String username = user.getName();
        for ( String key : user.getAttributes().keySet()) {
            System.out.println("key: " + key + " value: " + user.getAttributes().get(key));
        }
        for ( GrantedAuthority authority : user.getAuthorities()) {
            System.out.println("authority: " + authority);
        }
        //If account is null (is not created yet), then the gateway creates the new account
        try {
            accountMono = webClientBuilder.build().get().uri(URI.create("/account/" + username)).retrieve().bodyToMono(Account.class);
        } catch (Exception e) {
            logger.info("Exception Catched: " + e + ": " + e.getMessage());
            accountMono = webClientBuilder.build().post().uri(URI.create("/account/create/" + username)).retrieve().bodyToMono(Account.class);
        }
//        Flux<Account> accounts = webClientBuilder.build()
//                .get()
//                .uri("/account/" + username)
//                .attributes(oauth2AuthorizedClient(gatewayClient))
//                .retrieve()
//                .bodyToFlux(Account.class);
//        IReactiveDataDriverContextVariable accountDataDriver = new ReactiveDataDriverContextVariable(accounts, 1);
//        model.addAttribute("accounts", accountDataDriver);
        model.addAttribute("account", accountMono);
        return "index";
    }
    @RequestMapping(value = "/datapoints/{accountName}")
    public String showDatapointsPage(@PathVariable String accountName, Model model) {
        Flux<Datapoint> datapoints = webClientBuilder.build()
                .get()
                .uri("/stats/" + accountName)
                .retrieve()
                .bodyToFlux(Datapoint.class)
                .onErrorReturn(new Datapoint());
        model.addAttribute("datapoints", new ReactiveDataDriverContextVariable(datapoints, 1));
        return "datapoints";
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
