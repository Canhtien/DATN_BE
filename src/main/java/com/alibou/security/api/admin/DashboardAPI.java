package com.alibou.security.api.admin;

import com.alibou.security.model.dto.*;
import com.alibou.security.service.JPA.DashboardServiceJPA;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class DashboardAPI {

    private final DashboardServiceJPA dashboardService;

    @GetMapping("/overview")
    public ResponseEntity<DashboardOverviewDTO> getOverview() {
        DashboardOverviewDTO overview = dashboardService.getOverview();
        return ResponseEntity.ok(overview);
    }

    @GetMapping("/users")
    public ResponseEntity<UserStatisticsDTO> getUserStats() {
        return ResponseEntity.ok(dashboardService.getUserStatistics());
    }
    @GetMapping("/theaters")
    public ResponseEntity<TheaterStatisticsDTO> getTheaterStats() {
        return ResponseEntity.ok(dashboardService.getTheaterStatistics());
    }

    @GetMapping("/revenue/by-movie")
    public ResponseEntity<List<MovieRevenueDTO>> getRevenueByMovie() {
        return ResponseEntity.ok(dashboardService.getMovieRevenue());
    }

    @GetMapping("/revenue/by-theater")
    public ResponseEntity<List<TheaterRevenueDTO>> getRevenueByTheater() {
        return ResponseEntity.ok(dashboardService.getTheaterRevenue());
    }

}