package com.alibou.security.api.user;

import com.alibou.security.model.request.ChangePasswordRequest;
import com.alibou.security.model.response.TheaterResponse;
import com.alibou.security.model.response.UserResponse;
import com.alibou.security.model.response.interfaces.ShowtimeResponseInterface;
import com.alibou.security.service.JPA.MovieServiceJPA;
import com.alibou.security.service.JPA.ShowtimeServiceJPA;
import com.alibou.security.service.JPA.TheaterServiceJPA;
import com.alibou.security.service.JPA.UserServiceJPA;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserAPI {

    private static final Logger logger = LoggerFactory.getLogger(UserAPI.class);

    private final UserServiceJPA service;
    private final UserServiceJPA userService;
    @Autowired
    MovieServiceJPA movieService;
    @Autowired
    ShowtimeServiceJPA showtimeService;
    @Autowired
    TheaterServiceJPA theaterService;

//    @GetMapping("/movies")
//    public ResponseEntity<List<Movie>> getMovieList() {
//
//        try {
//
//            List<Movie> movies= movieService.getAllMovies();
//            logger.info("Movie list retrieved successfully");
//            return ResponseEntity.status(200).body(movies);
//
//        } catch (Exception e) {
//            logger.error("Error getting movies: {}", e.getMessage());
//            return ResponseEntity.status(500).body(null);
//        }
//
//    }
//    @GetMapping("/movies/{id}")
//    public ResponseEntity<?> getMovie(@PathVariable int id) {
//        try {
//
//            MovieResponse movieResponse = movieService.getMovieById(id);
//            logger.info("Movie retrieved successfully: {}", movieResponse);
//            return ResponseEntity.status(200).body(movieResponse);
//
//        } catch (Exception e) {
//            logger.error("Error getting movie: {}", e.getMessage());
//            return ResponseEntity.status(500).body(null);
//        }
//    }
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

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        if (request == null) {
            logger.warn("ChangePasswordRequest is null for user: {}", connectedUser.getName());
            return ResponseEntity.badRequest().body("ChangePasswordRequest cannot be null"); // 400 Bad Request
        }

        try {
            service.changePassword(request, connectedUser);
            logger.info("Password change request processed for user: {}", connectedUser.getName());
            return ResponseEntity.ok().build(); // 200 OK
        } catch (Exception e) {
            logger.error("Error changing password for user: {}", connectedUser.getName(), e);
            return ResponseEntity.status(500).body("Error changing password"); // 500 Internal Server Error
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        try {
            UserResponse userResponse = userService.getUserInfoById(id);
            logger.info("User found: {}", userResponse);
            return ResponseEntity.ok().body(userResponse);
        }catch (Exception e) {
            logger.error("Error getting user: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error getting user");
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        logger.error("Unhandled exception: {}", e.getMessage());
        return ResponseEntity.status(500).body("Internal server error"); // 500 Internal Server Error
    }

}
