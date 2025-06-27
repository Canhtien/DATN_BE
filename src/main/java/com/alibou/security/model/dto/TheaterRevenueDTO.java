package com.alibou.security.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TheaterRevenueDTO {
    private String theaterName;
    private BigDecimal totalRevenue;
}
