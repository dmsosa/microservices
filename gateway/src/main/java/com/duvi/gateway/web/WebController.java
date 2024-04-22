package com.duvi.gateway.web;

import com.duvi.gateway.model.Account;
import com.duvi.gateway.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private DiscoveryClient discoveryClient;
    public WebController(WebClient.Builder webClientBuilder, DiscoveryClient discoveryClient) {
        this.webClientBuilder = webClientBuilder;
        this.discoveryClient = discoveryClient;
    }
    @RequestMapping(method = {RequestMethod.GET}, value = "/index")
    public String showIndexPage(Model model) {
        Flux<Account> account = webClientBuilder.build().get().uri("http://gateway:8061/account/demo").retrieve().bodyToFlux(Account.class);
        model.addAttribute("accounts", new ReactiveDataDriverContextVariable(account, 1));
        return "index";
    }
    @RequestMapping(method = {RequestMethod.GET}, value = "/login")
    public String showLoginPage(@ModelAttribute User user, BindingResult bindingResult, Model model) {
        return "login";
    }
    @RequestMapping(method = {RequestMethod.POST}, value = "/register")
    public String registerNewUser(@ModelAttribute User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        model.addAttribute("registered", true);
        webClientBuilder.build().post().uri("http://localhost:9000/api/uaa/register").bodyValue(user);
        return "login";
    }

//    @RequestMapping(method = {RequestMethod.POST}, value = "/save/{accountName}")
//    public String saveAccountChanges(@PathVariable String accountName, Model model) {
//        webClientBuilder.build().post().uri("http://gateway/account/demo").
//    }
}
