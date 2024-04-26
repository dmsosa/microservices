package com.duvi.gateway.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DatapointId {
    private String accountName;
    private LocalDateTime dataDate;
}
