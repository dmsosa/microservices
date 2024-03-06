package com.duvi.services.stats.controller;

import com.duvi.services.stats.domain.Account;
import com.duvi.services.stats.domain.Datapoint;
import com.duvi.services.stats.service.StatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stat")
public class StatController {

    private StatService statService;

    public StatController(StatService statService) {
        this.statService = statService;
    }
    @GetMapping("/{accountName}")
    public ResponseEntity<Datapoint> getStatsOfAccount(@RequestParam String accountName) {
        Datapoint datapoint = statService.getStatsOfAccount(accountName);
        return new ResponseEntity<>(datapoint, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Datapoint> saveChanges(@RequestBody Account account) {
        Datapoint datapoint = statService.saveChanges(account);
        return new ResponseEntity<>(datapoint, HttpStatus.OK);
    }
}
