package com.alibou.security.repository;

import com.alibou.security.entity.SeatRow;
import com.alibou.security.model.dto.SeatTypeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SeatRowRepository extends JpaRepository<SeatRow, Long> {
//    boolean existsByTitle(String title);
    Optional<SeatRow> findById(Long Id);

    @Query("SELECT s FROM SeatRow s WHERE s.hall.id = :hallId")
    List<SeatRow> findByHallId(@Param("hallId") Long hallId);
}
