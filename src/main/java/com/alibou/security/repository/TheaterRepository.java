package com.alibou.security.repository;

import com.alibou.security.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TheaterRepository extends JpaRepository<Theater, Long> {
    Optional<Theater> findByName(String name);
    Optional<Theater> findById(Long id);


    @Query(value = """
    SELECT t.id AS theater_id, t.name AS theater_name,
           h.id AS hall_id, h.name AS hall_name,
           m.id AS movie_id, m.title AS movie_title, m.poster_url as posterUrl,
           s.id AS showtime_id, s.show_time,
            t.location AS locationVal, t.code AS codeVal,
            t.theater_img as theaterImg
    FROM theaters t
    JOIN halls h ON h.theater_id = t.id
    JOIN showtimes s ON s.hall_id = h.id
    JOIN movies m ON m.id = s.movie_id
    WHERE s.show_time >= CURDATE() - INTERVAL 1 MONTH
      AND (:name IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%')))
      AND (:location IS NULL OR LOWER(t.location) LIKE LOWER(CONCAT('%', :location, '%')))
      AND (:code IS NULL OR t.code = :code)
    ORDER BY t.id, h.id, m.id, s.id
    """, nativeQuery = true)
    List<Object[]> findGroupedTheaterMovieData(
            @Param("name") String name,
            @Param("location") String location,
            @Param("code") String code
    );

    @Query(value = """
            SELECT
              t.id AS theater_id, t.name AS theater_name, t.location, t.phone,
              st.id AS showtime_id, st.show_time,
              h.id AS hall_id, h.name AS hall_name, h.status AS hall_status,
              sr.id AS seat_row_id, sr.row_name,
              s.id AS seat_id, s.seat_number, s.status AS seat_status,
              stype.id AS seat_type_id, style.code AS seat_type_code, stype.price AS seat_price
            FROM theaters t
            JOIN showtimes st ON st.theater_id = t.id
            JOIN halls h ON h.id = st.hall_id
            JOIN seat_rows sr ON sr.hall_id = h.id
            JOIN seats s ON s.seat_row_id = sr.id
            JOIN seat_types stype ON stype.id = s.seat_type_id
            WHERE st.movie_id = :movieId
              AND DATE(st.show_time) = :date
            ORDER BY t.id, st.id, h.id, sr.id, s.seat_number
    """, nativeQuery = true)
    List<Object[]> findShowStructureByMovieIdAndDate(@Param("movieId") Long movieId, @Param("date") LocalDate date);

    @Query("SELECT COUNT(t) FROM Theater t")
    long countAllTheaters();
}
