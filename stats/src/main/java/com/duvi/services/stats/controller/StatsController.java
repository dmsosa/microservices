package com.duvi.services.stats.controller;

import com.duvi.services.stats.domain.AccountDTO;
import com.duvi.services.stats.domain.Stats;
import com.duvi.services.stats.service.StatsService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("")
public class StatsController {

    private StatsService statsService;
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/{accountName}")
    public Flux<Stats> getStatsOfAccount(@PathVariable String accountName) {
        return statsService.getStatsOfAccountByName(accountName);
    }
    @PostMapping("/save")
    public Mono<Stats> saveStatsOfAccount(@RequestBody AccountDTO accountDTO) {
        return statsService.createStats(accountDTO);
    }
}
