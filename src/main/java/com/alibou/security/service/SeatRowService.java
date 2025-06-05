package com.alibou.security.service;

import com.alibou.security.model.dto.SeatRowDTO;

import java.util.List;

public interface SeatRowService {
    List<SeatRowDTO> getAll();
    SeatRowDTO getById(Long id);
    SeatRowDTO create(SeatRowDTO dto);
    SeatRowDTO update(Long id, SeatRowDTO dto);
    void delete(Long id);
}
