package com.alibou.security.model.dto;

import lombok.Data;

import java.util.Map;

@Data
public class UserStatisticsDTO {
    private long totalUsers;
    private long newUsersToday;
    private long newUsersThisMonth;
    private Map<String, Long> usersByRole;
}