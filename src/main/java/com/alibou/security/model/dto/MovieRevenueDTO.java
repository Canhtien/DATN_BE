package com.alibou.security.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRevenueDTO {
    private String movieTitle;
    private BigDecimal totalRevenue;
}