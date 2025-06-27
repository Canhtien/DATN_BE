package com.alibou.security.model.dto;

import lombok.Data;

import java.util.Map;

@Data
public class TheaterStatisticsDTO {
    private long totalTheaters;
    private long totalHalls;
    private Map<String, Long> hallsByStatus;
}