package com.duvi.services.noti.client;

import com.duvi.services.noti.domain.dto.StatsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.awt.*;

@FeignClient(name = "stats", fallback = StatsClientFallback.class)
public interface StatsClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/stats/{accountName}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public StatsDTO getStatsOfAccount(String accountName);
}
