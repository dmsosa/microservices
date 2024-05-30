package com.duvi.services.noti.domain.dto;


import java.math.BigDecimal;

public record ItemDTO(
        Long id,
        String accountName,
        String title,
        String icon,
        BigDecimal amount
){
}
