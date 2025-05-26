package com.alibou.security.api.guest;

import com.alibou.security.api.user.UserAPI;
import com.alibou.security.entity.Movie;
import com.alibou.security.model.response.MovieResponse;
import com.alibou.security.model.response.TheaterResponse;
import com.alibou.security.model.response.interfaces.ShowtimeResponseInterface;
import com.alibou.security.service.MovieService;
import com.alibou.security.service.ShowtimeService;
import com.alibou.security.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/guest") // Tách endpoint movies ra API riêng
@RequiredArgsConstructor
public class GetMovieAPI {
    private static final Logger logger = LoggerFactory.getLogger(UserAPI.class);
    private final MovieService movieService;
    private final ShowtimeService showtimeService;
    private final TheaterService theaterService;

    @GetMapping ("/movies")
    public ResponseEntity<List<Movie>> getMovieList() {
        List<Movie> movies = movieService.getAllMovies(); // Lấy danh sách phim từ service
        return ResponseEntity.ok(movies);
    }

    //    @GetMapping("/{id}")
//    public ResponseEntity<Movie> getMovie(@PathVariable int id) {
//        Movie movie = movieService.getMovieById(id); // Lấy phim theo ID
//        return ResponseEntity.ok(movie);
//    }
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

}