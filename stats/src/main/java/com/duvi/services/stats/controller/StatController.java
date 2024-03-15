package com.duvi.services.stats.controller;

import com.duvi.services.stats.domain.Account;
import com.duvi.services.stats.domain.Datapoint;
import com.duvi.services.stats.service.StatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stat")
public class StatController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private StatService statService;

    public StatController(StatService statService) {
        this.statService = statService;
    }
    @GetMapping("")
    public ResponseEntity<Datapoint> getStatsOfAccount(@RequestBody Account account) {
        Datapoint datapoint = statService.getStatsOfAccount(account);
        return new ResponseEntity<>(datapoint, HttpStatus.OK);
    }

    @GetMapping("/{accountName}")
    public ResponseEntity<Datapoint> getStatsOfAccountByName(@PathVariable String accountName) {
        Datapoint datapoint = statService.getStatsOfAccountByName(accountName);
        return new ResponseEntity<>(datapoint, HttpStatus.OK);
    }
    @PostMapping("")
    public void saveChanges(@RequestBody Account account) {
        Datapoint datapoint = statService.saveChanges(account);
        logger.info("Changes for account %1$s at %2$s saved successfully".formatted(datapoint.getId().getAccountName(), datapoint.getId().getDataDate()));
    }
}
