package com.alibou.security.repository;

import com.alibou.security.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    boolean existsById(Long id);
//    Optional<Seat> findById(Long Id);

    List<Seat> findBySeatRow_Hall_Id(Long hallId);

    Optional<Seat> findBySeatNumberAndSeatRowId(Integer seatNumber, Long seatRowId);
}