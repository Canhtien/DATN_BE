package com.alibou.security.service.JPA;

import com.alibou.security.entity.Movie;
import com.alibou.security.entity.MovieReview;
import com.alibou.security.entity.Showtime;
import com.alibou.security.mapper.MovieMapper;
import com.alibou.security.mapper.MovieReviewMapper;
import com.alibou.security.model.dto.*;
import com.alibou.security.model.request.MovieRequest;
import com.alibou.security.model.response.MovieResponse;
import com.alibou.security.model.response.MovieReviewResponse;
import com.alibou.security.model.response.ShowtimeResponse;
import com.alibou.security.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieServiceJPA {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieReviewRepository movieReviewRepository;

    @Autowired
    ShowTimeRepository showTimeRepository;

    @Autowired
    MovieMapper movieMapper;

    @Autowired
    MovieReviewMapper movieReviewMapper;
    @Autowired
    private UserServiceJPA userService;
    @Autowired
    private DiscountApplicationRepository discountApplicationRepository;
    @Autowired
    private TicketRepository ticketRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Page<MovieLiteDTO> searchMovies(String title, String genre, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return movieRepository
                .searchByTitleAndGenre(title, genre, pageable)
                .map(movieMapper::toDto);
    }
    public List<MovieDTO> getTopMoviesPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Movie> movies = movieRepository.findTopWithPagination(pageable);
        return movies.stream()
                .map(movie -> new MovieDTO(
                        movie.getId(),
                        movie.getTitle(),
                        movie.getPosterUrl(),
                        movie.getRating()
                ))
                .collect(Collectors.toList());
    }

    public List<MovieDTO> getShowingMoviesPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Movie> movies = movieRepository.findShowingWithPagination(pageable);
        return movies.stream()
                .map(movie -> new MovieDTO(
                        movie.getId(),
                        movie.getTitle(),
                        movie.getPosterUrl(),
                        movie.getRating()
                ))
                .collect(Collectors.toList());
    }
    public List<MovieDTO> getCommingSoonMoviesPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Movie> movies = movieRepository.findCommingSoonWithPagination(pageable);
        return movies.stream()
                .map(movie -> new MovieDTO(
                        movie.getId(),
                        movie.getTitle(),
                        movie.getPosterUrl(),
                        movie.getRating()
                ))
                .collect(Collectors.toList());
    }

    public MovieResponse getMovieById(long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new ApplicationContextException("Movies not found"));

        double totalRating = movieReviewRepository.sumRatingByMovieId(id);
        int numberOfReviews = movieReviewRepository.countReviewsByMovieId(id);

        double averageRating = numberOfReviews > 0 ? totalRating / numberOfReviews : 0.0;

        movie.setRating(averageRating);
        Movie savedMovie = movieRepository.save(movie);
//        movieMapper.toMovieResponse(savedMovie);
        MovieResponse response = movieMapper.toMovieResponse(savedMovie);

        List<Showtime> showtimes = showTimeRepository.findShowtimesByMovieId(id);
        Set<ShowtimeResponse> showtimeResponses = showtimes.stream()
                .map(showTime -> {
                    ShowtimeResponse showtimeResponse = new ShowtimeResponse();
                    showtimeResponse.setShowTime(showTime.getShowTime());
                    showtimeResponse.setId(showTime.getId());
                    showtimeResponse.setMovieTitle(showTime.getMovie().getTitle());
                    // Chỉ gán "N/A" nếu không có dữ liệu thực tế
                    showtimeResponse.setTheaterName(showTime.getTheater().getName());
                    showtimeResponse.setHallName(showTime.getHall().getName());
                    showtimeResponse.setHallId(showTime.getHall().getId());
                    return showtimeResponse;
                })
                .collect(Collectors.toSet());
        response.setShowtimes(showtimeResponses);

        // Lấy và ánh xạ reviews, trả về Set rỗng nếu không có review nào
        List<MovieReview> movieReviews = movieReviewRepository.findMovieReviewByMovieId(id);
        Set<MovieReviewResponse> movieReviewResponses = movieReviews.stream()
                .map(movieReview -> {
                    MovieReviewResponse movieReviewResponse = new MovieReviewResponse();
                    movieReviewResponse.setUsername(movieReview.getUser().getUsername());
                    movieReviewResponse.setContent(movieReview.getContent());
                    movieReviewResponse.setMovieTitle(movieReview.getMovie().getTitle());
                    movieReviewResponse.setRating(movieReview.getRating());
                    movieReviewResponse.setCreatedAt(movieReview.getCreatedAt());
                    movieReviewResponse.setUpdatedAt(movieReview.getUpdatedAt());
                    movieReviewResponse.setCreatedBy(movieReview.getCreatedBy());
                    movieReviewResponse.setUpdatedBy(movieReview.getUpdatedBy());
                    return movieReviewResponse;
                })
                .collect(Collectors.toSet());
        response.setReviews(movieReviewResponses.isEmpty() ? Collections.emptySet() : movieReviewResponses);

        return response;
    }

    public MovieResponse updateMovieById(long id, MovieRequest request) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new ApplicationContextException("Movies not found"));
        movieMapper.updateMovie(movie, request);

        movie.setCreatedAt(movie.getCreatedAt());
        movie.setUpdatedAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        movie.setUpdatedBy(userService.getCurrentUserId());
        // Cập nhật các review liên quan nếu cần
        movie.getReviews().forEach(review -> {
            // Thực hiện các thay đổi đối với review dựa trên yêu cầu
            movieReviewRepository.save(review);
        });

        return movieMapper.toMovieResponse(movieRepository.save(movie));
    }

    @Transactional
    public void deleteMovieById(long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ApplicationContextException("Movie not found"));


        // Delete associated showtimes
        ticketRepository.setShowtimeIdToNull(movie.getId());
        showTimeRepository.setMovieIdToNull(movie.getId());
        movieReviewRepository.deleteByMovieId(movie.getId());
        // Delete movie
        movieRepository.deleteById(id);
    }


    public MovieResponse addMovie(MovieRequest request) throws IOException {

        if (movieRepository.existsByTitle(request.getTitle())) {
            throw new ApplicationContextException("Movie already exists");
        }

        Movie movie = movieMapper.toMovie(request);
//        movie.setId(null);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        movie.setCreatedAt(currentTime.toLocalDateTime());
        movie.setUpdatedAt(currentTime.toLocalDateTime());
        movie.setCreatedBy(userService.getCurrentUserId());


        try {
            Movie savedMovie = movieRepository.save(movie);

            return movieMapper.toMovieResponse(savedMovie);

        } catch (Exception e) {
            log.error("Movie not added. Error: {}", e.getMessage(), e);
            throw new ApplicationContextException("Movie not added: " + e.getMessage());
        }
    }
    public List<MovieDTO> getMovieShowtimeSeatHierarchy(Long theaterId, LocalDate date) {
        List<Object[]> rows = movieRepository.findMovieShowtimeSeats(theaterId, date);
        Map<Long, MovieDTO> movieMap = new LinkedHashMap<>();

        for (Object[] row : rows) {
            Long movieId = ((Number) row[0]).longValue();
            String movieTitle = (String) row[1];
            Long showtimeId = ((Number) row[2]).longValue();
            LocalDateTime showTime = ((Timestamp) row[3]).toLocalDateTime();
            Long seatId = ((Number) row[4]).longValue();
            Integer seatNumber = ((Number) row[5]).intValue();
            String seatStatus = (String) row[6];
            Long seatTypeId = ((Number) row[7]).longValue();
            String seatTypeCode = (String) row[8];
            BigDecimal seatPrice = (BigDecimal) row[9];

            MovieDTO movie = movieMap.computeIfAbsent(movieId, id -> new MovieDTO(id, movieTitle,null, null));
            ShowTimeDTO showtime = movie.getShowtimes().stream()
                    .filter(st -> st.getId().equals(showtimeId))
                    .findFirst()
                    .orElseGet(() -> {
                        ShowTimeDTO newSt = new ShowTimeDTO(showtimeId, showTime);
                        movie.getShowtimes().add(newSt);
                        return newSt;
                    });

            showtime.getSeats().add(new SeatDTO(seatId, seatNumber, seatStatus, seatTypeId, seatTypeCode));
        }

        return new ArrayList<>(movieMap.values());
    }
}
