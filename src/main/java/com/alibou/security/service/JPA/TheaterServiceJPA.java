package com.alibou.security.service.JPA;

import com.alibou.security.config.GeneralMapper;
import com.alibou.security.entity.Theater;
import com.alibou.security.model.dto.*;
import com.alibou.security.model.request.TheaterRequest;
import com.alibou.security.model.response.TheaterResponse;
import com.alibou.security.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TheaterServiceJPA {

    private static final Logger logger = LoggerFactory.getLogger(TheaterServiceJPA.class);
    private final TheaterRepository repository;
    private final UserServiceJPA userService;
    private final GeneralMapper generalMapper;

    public TheaterResponse add(TheaterRequest request) {
        var existingTheater = repository.findByName(request.getName());
        if (existingTheater.isPresent()) {
            throw new IllegalArgumentException("Theater's name was exist");
        }
        var theater = generalMapper.mapToEntity(request, Theater.class);
        theater.setCreatedBy(userService.getCurrentUserId());
        theater.setCreatedAt(LocalDateTime.now());
        repository.save(theater);
        logger.info("Theater added successfully: {}", theater);
        return generalMapper.mapToDTO(theater, TheaterResponse.class);
    }

    public Theater getById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public TheaterResponse change(TheaterRequest request, Long id) {
        var existingTheater = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Theater not found"));
        generalMapper.mapToEntity(request, existingTheater);
        existingTheater.setUpdatedBy(userService.getCurrentUserId());
        existingTheater.setUpdatedAt(LocalDateTime.now());
        repository.save(existingTheater);
        logger.info("Theater updated successfully: {}", existingTheater);
        return generalMapper.mapToDTO(existingTheater, TheaterResponse.class);
    }

    public void delete(Long id) {
        var existingTheater = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Theater not found"));
        repository.deleteById(existingTheater.getId());
        logger.info("Theater deleted successfully: {}", id);
    }

    public List<TheaterResponse> findAll() {
        List<Theater> theaters = repository.findAll();
        logger.info("Theaters retrieved successfully");
        return theaters.stream()
                .map(theater -> generalMapper.mapToDTO(theater, TheaterResponse.class))
                .collect(Collectors.toList());
    }
    // dành cho cms
    public List<TheaterDTO> getTheaterHierarchy(String locationVal, String codeVal) {
        List<Object[]> rows = repository.findGroupedTheaterMovieData(locationVal, codeVal);
        Map<Long, TheaterDTO> theaterMap = new LinkedHashMap<>();

        for (Object[] row : rows) {
            Long theaterId = ((Number) row[0]).longValue();
            String theaterName = (String) row[1];
            Long hallId = ((Number) row[2]).longValue();
            String hallName = (String) row[3];
            Long movieId = ((Number) row[4]).longValue();
            String movieTitle = (String) row[5];
            String posterUrl = (String) row[6];
            Long showtimeId = ((Number) row[7]).longValue();
            LocalDateTime startTime = ((Timestamp) row[8]).toLocalDateTime();
            String location = (String) row[9];
            String code = (String) row[10];
            String theatetImg = (String) row[11];
            TheaterDTO theater = theaterMap.computeIfAbsent(theaterId, id -> new TheaterDTO(theaterId, theaterName,location,code,theatetImg));
            HallDTO hall = theater.getHalls().stream()
                    .filter(h -> h.getId().equals(hallId))
                    .findFirst()
                    .orElseGet(() -> {
                        HallDTO newHall = new HallDTO(hallId, hallName);
                        theater.getHalls().add(newHall);
                        return newHall;
                    });

            MovieDTO movie = hall.getMovies().stream()
                    .filter(m -> m.getId().equals(movieId))
                    .findFirst()
                    .orElseGet(() -> {
                        MovieDTO newMovie = new MovieDTO(movieId, movieTitle, posterUrl);
                        hall.getMovies().add(newMovie);
                        return newMovie;
                    });

            movie.addShowtime(new ShowTimeDTO(showtimeId, startTime));
        }

        return new ArrayList<>(theaterMap.values());
    }
    //dành cho movie-detail
    public List<TheaterDTO> getTheaterHierarchyByMovieId(Long movieId, LocalDate date){
        List<Object[]> result = repository.findShowStructureByMovieIdAndDate(movieId, date);
        List<TheaterDTO> structuredData = buildHierarchy(result);
        return structuredData;
    }

    public List<TheaterDTO> buildHierarchy(List<Object[]> resultList) {
        Map<Long, TheaterDTO> theaterMap = new HashMap<>();
        Map<Long, ShowTimeDTO> showtimeMap = new HashMap<>();
        Map<Long, HallDTO> hallMap = new HashMap<>();
        Map<Long, SeatRowDTO> rowMap = new HashMap<>();

        for (Object[] row : resultList) {
            // Extract columns
            Long theaterId = ((Number) row[0]).longValue();
            String theaterName = (String) row[1];
            String location = (String) row[2];
            String phone = (String) row[3];

            Long showtimeId = ((Number) row[4]).longValue();
            LocalDateTime showTime = ((Timestamp) row[5]).toLocalDateTime();

            Long hallId = ((Number) row[6]).longValue();
            String hallName = (String) row[7];
            String hallStatus = (String) row[8];

            Long rowId = ((Number) row[9]).longValue();
            String rowName = (String) row[10];

            Long seatId = ((Number) row[11]).longValue();
            Integer seatNumber = ((Number) row[12]).intValue();
            String seatStatus = (String) row[13];

            Long seatType = ((Number) row[14]).longValue();
            String seatTypeCode = (String) row[15];
            BigDecimal seatPrice = (BigDecimal) row[16];

            // Theater
            TheaterDTO theater = theaterMap.computeIfAbsent(theaterId, id -> {
                TheaterDTO t = new TheaterDTO();
                t.setId(id);
                t.setName(theaterName);
                t.setLocation(location);
                t.setPhone(phone);
                return t;
            });

            // Showtime
            ShowTimeDTO showtime = showtimeMap.computeIfAbsent(showtimeId, id -> {
                ShowTimeDTO s = new ShowTimeDTO();
                s.setId(id);
                s.setStartTime(showTime);
                theater.getShowtimes().add(s);
                return s;
            });

            // Hall
            HallDTO hall = hallMap.computeIfAbsent(hallId, id -> {
                HallDTO h = new HallDTO();
                h.setId(id);
                h.setName(hallName);
                showtime.getHalls().add(h);
                return h;
            });

            // Seat Row
            SeatRowDTO seatRow = rowMap.computeIfAbsent(rowId, id -> {
                SeatRowDTO r = new SeatRowDTO();
                r.setId(id);
                r.setRowName(rowName);
                hall.getSeatRows().add(r);
                return r;
            });

            // Seat
            SeatDTO seat = new SeatDTO();
            seat.setId(seatId);
            seat.setSeatNumber(seatNumber);
            seat.setSeatType(seatType);
            seat.setSeatTypeCode(seatTypeCode);
            seat.setSeatPrice(seatPrice);

            seatRow.getSeats().add(seat);
        }

        return new ArrayList<>(theaterMap.values());
    }

}