package com.alibou.security.service.JPA;

import com.alibou.security.model.dto.*;
import com.alibou.security.repository.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceJPA {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TheaterRepository theaterRepository;
    @Autowired
    HallRepository hallRepository;
    private final TicketRepository ticketRepository;
    private final ShowTimeRepository showtimeRepository;


    public DashboardOverviewDTO getOverview() {
        DashboardOverviewDTO dto = new DashboardOverviewDTO();

        dto.setTotalTicketsToday(ticketRepository.countTicketsToday());
        dto.setTotalRevenueToday(ticketRepository.sumRevenueToday());

        dto.setTotalTicketsThisMonth(ticketRepository.countTicketsThisMonth());
        dto.setTotalRevenueThisMonth(ticketRepository.sumRevenueThisMonth());

        dto.setTotalShowtimesToday(showtimeRepository.countShowtimesToday());
        dto.setTotalShowtimesThisWeek(showtimeRepository.countShowtimesThisWeek());

        return dto;
    }


    public UserStatisticsDTO getUserStatistics() {
        UserStatisticsDTO dto = new UserStatisticsDTO();
        dto.setTotalUsers(userRepository.countAllUsers());
        dto.setNewUsersToday(userRepository.countNewUsersToday());
        dto.setNewUsersThisMonth(userRepository.countNewUsersThisMonth());

        Map<String, Long> roleMap = new HashMap<>();
        for (Object[] row : userRepository.countUsersByRole()) {
            roleMap.put((String) row[0], (Long) row[1]);
        }
        dto.setUsersByRole(roleMap);

        return dto;
    }

    public TheaterStatisticsDTO getTheaterStatistics() {
        TheaterStatisticsDTO dto = new TheaterStatisticsDTO();

        dto.setTotalTheaters(theaterRepository.countAllTheaters());
        dto.setTotalHalls(hallRepository.countAllHalls());

        Map<String, Long> statusMap = new HashMap<>();
        for (Object[] row : hallRepository.countHallsByStatus()) {
            statusMap.put((String) row[0], (Long) row[1]);
        }
        dto.setHallsByStatus(statusMap);

        return dto;
    }

    public List<MovieRevenueDTO> getMovieRevenue() {
        List<Object[]> result = ticketRepository.getRevenueByMovie();
        return result.stream()
                .map(r -> new MovieRevenueDTO((String) r[0], (BigDecimal) r[1]))
                .collect(Collectors.toList());
    }

    public List<TheaterRevenueDTO> getTheaterRevenue() {
        List<Object[]> result = ticketRepository.getRevenueByTheater();
        return result.stream()
                .map(r -> new TheaterRevenueDTO((String) r[0], (BigDecimal) r[1]))
                .collect(Collectors.toList());
    }


}