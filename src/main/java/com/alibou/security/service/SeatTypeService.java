package com.alibou.security.service;



import com.alibou.security.model.dto.SeatTypeDTO;

import java.util.List;

public interface SeatTypeService {
    List<SeatTypeDTO> getAll();
    SeatTypeDTO getById(Long id);
    SeatTypeDTO create(SeatTypeDTO dto);
    SeatTypeDTO update(Long id, SeatTypeDTO dto);
    void delete(Long id);
}