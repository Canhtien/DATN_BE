package com.alibou.security.service;

import com.alibou.security.entity.Seat;
import com.alibou.security.model.dto.SeatDTO;

import java.util.List;

public interface SeatService {
    List<Seat> getAll();
    Seat create(SeatDTO dto);
    Seat update(Long id, SeatDTO dto);
    void delete(Long id);
    List<SeatDTO> getSeatsByHallId(Long hallId);
}
