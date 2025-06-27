package com.alibou.security.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardOverviewDTO {
    private long totalTicketsToday;
    private BigDecimal totalRevenueToday;
    private long totalTicketsThisMonth;
    private BigDecimal totalRevenueThisMonth;
    private long paidTickets;
    private long pendingTickets;
    private long cancelledTickets;
    private long totalShowtimesToday;
    private long totalShowtimesThisWeek;
}
