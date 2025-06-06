package com.alibou.security.repository;

import com.alibou.security.entity.Movie;
import com.alibou.security.model.response.MovieResponse;
import jakarta.persistence.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    boolean existsByTitle(String title);

    @Query("SELECT m FROM Movie m LEFT JOIN FETCH m.showtimes WHERE m.id = :id")
    Optional<Movie> findById(@Param("id") Long id);

    @Query(value = "SELECT m FROM Movie m",
            countQuery = "SELECT COUNT(m) FROM Movie m")
    Page<Movie> findAllWithPagination(Pageable pageable);

    @Query(value = "SELECT * FROM movies WHERE release_date >= CURDATE() - INTERVAL 1 MONTH ORDER BY rating DESC",
            countQuery = "SELECT COUNT(*) FROM movies WHERE release_date >= CURDATE() - INTERVAL 1 MONTH",
            nativeQuery = true)
    List<Movie> findTopWithPagination(Pageable pageable);

    @Query(value = "SELECT * FROM movies WHERE release_date >= CURDATE() - INTERVAL 1 MONTH",
            countQuery = "SELECT COUNT(*) FROM movies WHERE release_date >= CURDATE() - INTERVAL 1 MONTH",
            nativeQuery = true)
    List<Movie> findShowingWithPagination(Pageable pageable);

    @Query(value = "SELECT * FROM movies WHERE release_date >= CURDATE() - INTERVAL 2 MONTH ",
            countQuery = "SELECT COUNT(*) FROM movies WHERE release_date >= CURDATE() - INTERVAL 2 MONTH",
            nativeQuery = true)
    List<Movie> findCommingSoonWithPagination(Pageable pageable);

    @Query(value = """
    SELECT 
        m.id AS movie_id,
        m.title AS movie_title,
        st.id AS showtime_id,
        st.show_time,
        s.id AS seat_id,
        s.seat_number,
        sr.row_name AS seatRow,
        s.status AS seat_status,
        stype.id AS seat_type_id,
        stype.code AS seat_type_code,
        stype.price AS seat_price
    FROM 
        showtimes st
    JOIN movies m ON st.movie_id = m.id
    JOIN halls h ON st.hall_id = h.id
    JOIN seat_rows sr ON sr.hall_id = h.id
    JOIN seats s ON s.seat_row_id = sr.id
    JOIN seat_types stype ON s.seat_type_id = stype.id
    WHERE 
        st.theater_id = :theaterId
        AND DATE(st.show_time) = :date
    ORDER BY m.id, st.id, s.seat_number
""", nativeQuery = true)
    List<Object[]> findMovieShowtimeSeats(
            @Param("theaterId") Long theaterId,
            @Param("date") LocalDate date
    );
}
