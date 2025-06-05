package com.alibou.security.repository;

import com.alibou.security.entity.SeatType;
import com.alibou.security.model.dto.SeatTypeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType, Long> {
    boolean existsByCode(String code);
}
