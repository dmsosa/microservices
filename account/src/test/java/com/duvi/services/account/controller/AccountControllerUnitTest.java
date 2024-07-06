package com.duvi.services.account.controller;


import com.duvi.services.account.config.security.JwtCustomizer;
import com.duvi.services.account.config.security.SecurityConfig;
import com.duvi.services.account.model.dto.AccountDTO;
import com.duvi.services.account.model.dto.ItemDTO;
import com.duvi.services.account.model.enums.Currency;
import com.duvi.services.account.service.AccountService;
import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)

public class AccountControllerUnitTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void whenNoToken_ThenUnauthorized() throws Exception {
        Mockito.when(accountService.getAccountByName(Mockito.anyString())).then(invocationOnMock -> {
            String name = invocationOnMock.getArgument(0);
            return new AccountDTO(name, null, null, null, new ArrayList<ItemDTO>(), new ArrayList<ItemDTO>(), Currency.USD);
        });
        mockMvc.perform(get("/test")).andDo(print()).andExpect(status().isOk());
    }
}
