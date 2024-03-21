package com.duvi.services.stats.controller;

import com.duvi.services.stats.domain.AccountDTO;
import com.duvi.services.stats.domain.Datapoint;
import com.duvi.services.stats.service.StatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class StatsController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private StatService statService;

    public StatsController(StatService statService) {
        this.statService = statService;
    }
    @GetMapping("")
    public ResponseEntity<Datapoint> getStatsOfAccount(@RequestBody AccountDTO account) {
        Datapoint datapoint = statService.getStatsOfAccount(account);
        return new ResponseEntity<>(datapoint, HttpStatus.OK);
    }

    @GetMapping("/{accountName}")
    public ResponseEntity<Datapoint> getStatsOfAccountByName(@PathVariable String accountName) {
        Datapoint datapoint = statService.getStatsOfAccountByName(accountName);
        return new ResponseEntity<>(datapoint, HttpStatus.OK);
    }
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('SCOPE_read')")
    public void saveChanges(@RequestBody AccountDTO account) {
        Datapoint datapoint = statService.saveChanges(account);
        logger.info("Changes for account %1$s at %2$s saved successfully".formatted(datapoint.getId().getAccountName(), datapoint.getId().getDataDate()));
    }
}
