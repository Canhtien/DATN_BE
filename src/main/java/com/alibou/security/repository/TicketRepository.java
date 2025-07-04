package com.alibou.security.repository;

import com.alibou.security.entity.Showtime;
import com.alibou.security.entity.Ticket;
import com.alibou.security.enums.TicketStatus;
import com.alibou.security.model.response.TicketResponse;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    boolean existsById(Long id);

    void deleteByShowtimeId(Long showtimeId);

    List<Ticket> findByStatus(TicketStatus status);


    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Ticket t WHERE t.showtime.id = :showtimeId AND t.seat.id = :seatId")
    boolean existsByShowtimeIdAndSeatId(@Param("showtimeId") Long showtimeId, @Param("seatId") Long seatId);

    @Query("SELECT new com.alibou.security.model.response.TicketResponse(" +
            "t.id, " +
            "CONCAT(r.rowName, '', t.seat.seatNumber), " +  // lấy mã ghế thực tế
            "t.ticketType, " +
            "t.price, " +
            "t.serviceFee, " +
            "t.status, " +
            "t.createdAt, " +
            "t.updatedAt, " +
            "t.createdBy, " +
            "t.updatedBy, " +
            "s.showTime, " +
            "m.title, " +
            "th.name, " +
            "h.name) " +
            "FROM Ticket t " +
            "LEFT JOIN t.showtime s " +
            "LEFT JOIN s.movie m " +
            "LEFT JOIN s.theater th " +
            "LEFT JOIN s.hall h " +
            "LEFT JOIN t.seat seat " +
            "LEFT JOIN seat.seatRow r " +
            "WHERE t.user.id = :userId")
    List<TicketResponse> findAllByUserId(@Param("userId") Long userId);


    @Query(value = "SELECT new com.alibou.security.model.response.TicketResponse(t.id, t.seat.id, t.ticketType, t.price, " +
            "t.serviceFee, t.status, t.createdAt, t.updatedAt, t.createdBy, t.updatedBy, " +
            "s.showTime, m.title, th.name, h.name) " +
            "FROM Ticket t " +
            "LEFT JOIN t.showtime s " +
            "LEFT JOIN s.movie m " +
            "LEFT JOIN s.theater th " +
            "LEFT JOIN s.hall h " +
            "WHERE (:id IS NULL OR t.id = :id)",
            countQuery = "SELECT COUNT(t) FROM Ticket t")
    Page<TicketResponse> findAllTickets(@Param("id") Long id, Pageable pageable);

    @Query("SELECT t.status, t.seat.id FROM Ticket t WHERE t.showtime.id = :id")
    List<Object[]> findAllByShowtimeId(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Ticket t set t.showtime = null WHERE t.showtime.id IN (SELECT s.id FROM Showtime s WHERE s.movie.id = :movieId)")
    void setShowtimeIdToNull(@Param("movieId") Long movieId);

    @Modifying
    @Transactional
    @Query("UPDATE Ticket t set t.showtime = null WHERE t.showtime.id IN (SELECT s.id FROM Showtime s WHERE  s.id IN :showtimeId)")
    void setShowtimeIdToNullInDeleteShowtime(@Param("showtimeId") List<Long> showtimeId);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE DATE(t.createdAt) = CURRENT_DATE")
    long countTicketsToday();

    @Query("SELECT COALESCE(SUM(t.price + t.serviceFee), 0) FROM Ticket t WHERE DATE(t.createdAt) = CURRENT_DATE AND t.status IN ('PAID', 'USED')")
    BigDecimal sumRevenueToday();

    @Query("SELECT t.showtime.movie.title, SUM(t.price + t.serviceFee) " +
            "FROM Ticket t WHERE FUNCTION('DATE_FORMAT', t.createdAt, '%Y-%m') = :month " +
            "AND t.status IN ('PAID', 'USED') GROUP BY t.showtime.movie.title ORDER BY SUM(t.price + t.serviceFee) DESC")
    List<Object[]> findTopMoviesByRevenue(@Param("month") String month);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE MONTH(t.createdAt) = MONTH(CURRENT_DATE) AND YEAR(t.createdAt) = YEAR(CURRENT_DATE)")
    long countTicketsThisMonth();

    @Query("SELECT COALESCE(SUM(t.price + t.serviceFee), 0) FROM Ticket t WHERE MONTH(t.createdAt) = MONTH(CURRENT_DATE) AND YEAR(t.createdAt) = YEAR(CURRENT_DATE) AND t.status IN ('PAID', 'USED')")
    BigDecimal sumRevenueThisMonth();

    @Query("SELECT t.showtime.movie.title, SUM(t.price + t.serviceFee) " +
            "FROM Ticket t WHERE t.status IN ('PAID', 'USED') GROUP BY t.showtime.movie.title ORDER BY SUM(t.price + t.serviceFee) DESC")
    List<Object[]> getRevenueByMovie();

    @Query("SELECT t.showtime.theater.name, SUM(t.price + t.serviceFee) " +
            "FROM Ticket t WHERE t.status IN ('PAID', 'USED') GROUP BY t.showtime.theater.name ORDER BY SUM(t.price + t.serviceFee) DESC")
    List<Object[]> getRevenueByTheater();
}