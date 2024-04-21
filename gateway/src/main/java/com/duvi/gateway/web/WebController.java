package com.duvi.gateway.web;

import com.duvi.gateway.model.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@Controller
public class WebController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebClient.Builder webClientBuilder;
    public WebController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }
    @RequestMapping(method = {RequestMethod.GET}, value = "/index")
    public String index(Model model) {
        Flux<Account> account = webClientBuilder.build().get().uri("http://gateway:8061/account/demo").retrieve().bodyToFlux(Account.class);
        model.addAttribute("accounts", new ReactiveDataDriverContextVariable(account, 1));
        return "index";
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/save/{accountName}")
    public String saveAccountChanges(@PathVariable String accountName, Model model) {
        webClientBuilder.build().post().uri("http://gateway/account/demo").
    }
}
