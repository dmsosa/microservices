package com.duvi.services.stats.controller;

import com.duvi.services.stats.domain.dto.AccountDTO;
import com.duvi.services.stats.domain.dto.StatsDTO;
import com.duvi.services.stats.domain.exception.EntityExistsException;
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
    public Flux<StatsDTO> getStatsOfAccount(@PathVariable String accountName) {
        return statsService.getStatsOfAccountByName(accountName);
    }
    @PostMapping("/save")
    public Mono<Void> saveStatsOfAccount(@RequestBody AccountDTO accountDTO) throws EntityExistsException {
        return statsService.createStats(accountDTO);
    }
}
