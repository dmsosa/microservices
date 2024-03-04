package com.duvi.services.stats.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
public class DataPointId implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accountName;
    private LocalDateTime dataDate;
    public DataPointId(String accountName, LocalDateTime dataDate) {
        this.accountName = accountName;
        this.dataDate = dataDate;
    }

    @Override
    public String toString() {
        return "DataPointId{" +
                "account='" + accountName + "'" +
                ", date=" + dataDate +
                "}";
    }
}
