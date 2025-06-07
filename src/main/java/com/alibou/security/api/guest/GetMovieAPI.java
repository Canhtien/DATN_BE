package com.alibou.security.api.guest;

import com.alibou.security.api.user.UserAPI;
import com.alibou.security.entity.Movie;
import com.alibou.security.model.dto.MovieDTO;
import com.alibou.security.model.dto.TheaterDTO;
import com.alibou.security.model.response.MovieResponse;
import com.alibou.security.model.response.TheaterResponse;
import com.alibou.security.model.response.interfaces.ShowtimeResponseInterface;
import com.alibou.security.service.JPA.MovieServiceJPA;
import com.alibou.security.service.JPA.ShowtimeServiceJPA;
import com.alibou.security.service.JPA.TheaterServiceJPA;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/api/guest") // Tách endpoint movies ra API riêng
@RequiredArgsConstructor
public class GetMovieAPI {
    private static final Logger logger = LoggerFactory.getLogger(UserAPI.class);
    private final MovieServiceJPA movieService;
    private final ShowtimeServiceJPA showtimeService;
    private final TheaterServiceJPA theaterService;

    @GetMapping ("/movies")
    public ResponseEntity<List<Movie>> getMovieList() {
        List<Movie> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }
    //top phim
    @GetMapping ("/top-movies")
    public ResponseEntity<List<Movie>> getTopMovie(@RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10")int pagesize){
            return ResponseEntity.ok(movieService.getTopMoviesPaged(page, pagesize));
        }

    //đang chiếu
    @GetMapping ("/now-showing-movies")
    public ResponseEntity<List<Movie>> getShowingMovie(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "20")int pagesize){
        return ResponseEntity.ok(movieService.getShowingMoviesPaged(page, pagesize));
    }
    //comming soon
    @GetMapping ("/comming-soon-movies")
    public ResponseEntity<List<Movie>> getCommingSoonMovie(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "20")int pagesize){
        return ResponseEntity.ok(movieService.getCommingSoonMoviesPaged(page, pagesize));
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<?> getMovie(@PathVariable int id) {
        try {

            MovieResponse movieResponse = movieService.getMovieById(id);
            logger.info("Movie retrieved successfully: {}", movieResponse);
            return ResponseEntity.status(200).body(movieResponse);

        } catch (Exception e) {
            logger.error("Error getting movie: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
    @GetMapping("/showtimes")
    public ResponseEntity<List<ShowtimeResponseInterface>> getShowtimeList() {
        try {
            List<ShowtimeResponseInterface> showtimes = showtimeService.getAllShowtime();
//            List<ShowtimeResponse> showtimeResponses = showtimes.stream()
//                    .map(showtimeMapper::toshowtimeResponse) // Chuyển `Showtime` thành `ShowtimeResponse`
//                    .collect(Collectors.toList());
            logger.info("Showtime list retrieved successfully");
            return ResponseEntity.status(200).body(showtimes);
        } catch (Exception e) {
            logger.error("Error getting showtime: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
    @GetMapping("/theaters")
    public ResponseEntity<List<TheaterResponse>> findAllTheaters() {
        try {
            List<TheaterResponse> theaters = theaterService.findAll();
            logger.info("Retrieved all theaters successfully");
            return ResponseEntity.ok(theaters);
        } catch (Exception e) {
            logger.error("Failed to retrieve notifications: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
    @GetMapping("/with-halls-and-movies")
    public List<TheaterDTO> getTheaterHallMovieTree(@RequestParam(required = false)String location,
                                                    @RequestParam(required = false)String code) {
        return theaterService.getTheaterHierarchy(location, code);
    }

    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<MovieDTO>> getMovieShowtimeSeats(
            @PathVariable Long theaterId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<MovieDTO> data = movieService.getMovieShowtimeSeatHierarchy(theaterId, date);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/theater/movieId/{movieId}")
    public ResponseEntity<List<TheaterDTO>> getTheaterByMovieId(
            @PathVariable Long movieId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ){
        List<TheaterDTO> data = theaterService.getTheaterHierarchyByMovieId(movieId,date);
        return ResponseEntity.ok(data);
    }
}