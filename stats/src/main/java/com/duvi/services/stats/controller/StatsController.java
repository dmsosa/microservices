package com.duvi.services.stats.controller;

import com.duvi.services.stats.domain.AccountDTO;
import com.duvi.services.stats.domain.Datapoint;
import com.duvi.services.stats.domain.exception.EntityExistsException;
import com.duvi.services.stats.service.StatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Consumer;

@RefreshScope
@RestController
@RequestMapping("")
public class StatsController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private StatService statService;

    public StatsController(StatService statService) {
        this.statService = statService;
    }

    @GetMapping("/{accountName}")
    public ResponseEntity<Datapoint> getStatsOfAccountByName(@PathVariable String accountName) {
        List<Datapoint> datapoints = statService.getStatsOfAccountByName(accountName);
        return new ResponseEntity<>(datapoints.getFirst(), HttpStatus.OK);
    }
    @PostMapping("/save")
    public void saveChanges(@RequestBody AccountDTO account) throws EntityExistsException {
        Datapoint datapoint = statService.saveChanges(account);
        logger.info("Changes for account %1$s at %2$s saved successfully".formatted(datapoint.getId().getAccountName(), datapoint.getId().getDataDate()));
    }
}
