package com.alibou.security.service.Impl;

import com.alibou.security.entity.Hall;
import com.alibou.security.entity.SeatRow;
import com.alibou.security.model.dto.SeatRowDTO;
import com.alibou.security.repository.HallRepository;
import com.alibou.security.repository.SeatRowRepository;
import com.alibou.security.service.SeatRowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatRowServiceImpl implements SeatRowService {

    private final SeatRowRepository seatRowRepository;
    private final HallRepository hallRepository;

    @Override
    public List<SeatRowDTO> getAll() {
        return seatRowRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SeatRowDTO getById(Long id) {
        return seatRowRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("SeatRow not found"));
    }

    @Override
    public SeatRowDTO create(SeatRowDTO dto) {
        Hall hall = hallRepository.findById(dto.getHallId())
                .orElseThrow(() -> new RuntimeException("Hall not found"));

        SeatRow seatRow = SeatRow.builder()
                .rowName(dto.getRowName())
                .hall(hall)
                .build();

        return toDto(seatRowRepository.save(seatRow));
    }

    @Override
    public SeatRowDTO update(Long id, SeatRowDTO dto) {
        SeatRow seatRow = seatRowRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SeatRow not found"));

        seatRow.setRowName(dto.getRowName());
        seatRow.setHall(hallRepository.findById(dto.getHallId())
                .orElseThrow(() -> new RuntimeException("Hall not found")));

        return toDto(seatRowRepository.save(seatRow));
    }

    @Override
    public void delete(Long id) {
        seatRowRepository.deleteById(id);
    }

    private SeatRowDTO toDto(SeatRow seatRow) {
        return SeatRowDTO.builder()
                .id(seatRow.getId())
                .rowName(seatRow.getRowName())
                .hallId(seatRow.getHall() != null ? seatRow.getHall().getId() : null)
                .build();
    }
}
