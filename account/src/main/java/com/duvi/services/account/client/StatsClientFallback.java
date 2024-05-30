package com.duvi.services.account.client;

import com.duvi.services.account.model.dto.AccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatsClientFallback implements StatsClient {
    private Logger logger = LoggerFactory.getLogger(StatsClientFallback.class);

    @Override
    public void saveAccountStats(AccountDTO account) {
        logger.error("Error while saving stats for account: {}", account.name());
    }
}
