package com.duvi.services.noti.client;

import com.duvi.services.noti.domain.dto.AccountDTO;
import com.duvi.services.noti.domain.dto.StatsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StatsClientFallback implements StatsClient {
    private Logger logger = LoggerFactory.getLogger(StatsClientFallback.class);

    @Override
    public StatsDTO getStatsOfAccount(String accountName) {
        logger.error("Error while getting stats of account: {}", accountName);
        return new StatsDTO();
    }
}
