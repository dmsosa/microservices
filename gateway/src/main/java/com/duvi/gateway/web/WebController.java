package com.duvi.gateway.web;

import com.duvi.gateway.model.Account;
import com.duvi.gateway.model.AccountContextVar;
import com.duvi.gateway.model.Datapoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;



@Controller
public class WebController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebClient.Builder webClientBuilder;
    public WebController(WebClient.Builder webClientBuilder ) {
        this.webClientBuilder = webClientBuilder;
    }
    @RequestMapping(method = {RequestMethod.GET}, value = "/index")
    public String showIndexPage(
            @RegisteredOAuth2AuthorizedClient(registrationId = "gatewayClient") OAuth2AuthorizedClient authorizedClient, Model model) {

        Flux<Datapoint> datapoints = null;
        Flux<AccountContextVar> accountContextVar = webClientBuilder.build()
                .get()
                .uri("http://gateway:8061/account/demo")
                .attributes(oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToFlux(AccountContextVar.class);

//        webClientBuilder.build()
//                .get()
//                .uri("http://gateway:8061/stats/demo")
//                .attributes(oauth2AuthorizedClient(authorizedClient))
//                .retrieve()
//                .bodyToFlux(Datapoint.class)
//                .subscribe(datapoint -> accountContextVar.subscribe(
//                        acv -> acv.setDatapoint(datapoint)
//                ));
        model.addAttribute("accountContextVar", new ReactiveDataDriverContextVariable(accountContextVar, 1));
        return "index";
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/save/{accountName}")
    public String saveAccountChanges(@PathVariable String accountName, @Validated @ModelAttribute Account account, BindingResult bindingResult, Model model) {
        webClientBuilder.build().post().uri("/account/demo").bodyValue(account).retrieve().bodyToFlux(String.class);
        Flux<Account> accountFlux = webClientBuilder.build().get().uri("/account/demo").retrieve().bodyToFlux(Account.class);
        model.addAttribute("accounts", new ReactiveDataDriverContextVariable(accountFlux, 1));
        return "index";
    }
}
