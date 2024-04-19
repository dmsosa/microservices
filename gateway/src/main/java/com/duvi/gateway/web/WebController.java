package com.duvi.gateway.web;

import com.duvi.gateway.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Principal;

@Controller
public class WebController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebClient gatewayClient;
    public WebController(WebClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }
    @RequestMapping(method = {RequestMethod.GET}, value = "/index")
    public String index(Principal principal, Model model) {
        logger.info(principal.getName());
        Account account  = gatewayClient.get().uri("lb://account/api/account/demo").retrieve().bodyToMono(Account.class).block();
        model.addAttribute("account", account);
        return "index";
    }
}
