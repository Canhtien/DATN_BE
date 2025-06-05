package com.alibou.security.service.Impl;

import com.alibou.security.entity.Seat;
import com.alibou.security.entity.SeatRow;
import com.alibou.security.entity.SeatType;
import com.alibou.security.mapper.SeatMapper;
import com.alibou.security.model.dto.SeatDTO;
import com.alibou.security.model.dto.SeatTypeDTO;
import com.alibou.security.repository.SeatRepository;
import com.alibou.security.repository.SeatRowRepository;
import com.alibou.security.repository.SeatTypeRepository;
import com.alibou.security.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
    private final SeatRepository repo;
    private final SeatTypeRepository seatTypeRepo;
    private final SeatRowRepository seatRowRepo;
    private final SeatMapper seatMapper;
    public List<Seat> getAll() {
        return repo.findAll();
    }

    public Seat create(SeatDTO dto) {
        // Kiểm tra seat row tồn tại
        SeatRow seatRow = seatRowRepo.findById(dto.getSeatRowId())
                .orElseThrow(() -> new RuntimeException("SeatRow not found"));

        // Kiểm tra seat number đã tồn tại trong row chưa
        repo.findBySeatNumberAndSeatRowId(dto.getSeatNumber(), seatRow.getId())
                .ifPresent(seat -> {
                    throw new RuntimeException("Seat number already exists in this row");
                });

        Seat seat = new Seat();
        seat.setSeatNumber(dto.getSeatNumber());
        seat.setSeatRow(seatRow);

        // Set loại ghế
        SeatType seatType = seatTypeRepo.findById(dto.getSeatTypeId())
                .orElseThrow(() -> new RuntimeException("SeatType not found"));
        seat.setSeatType(seatType);

        return repo.save(seat);
    }

    public Seat update(Long id, SeatDTO dto) {
        Seat seat = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        seat.setSeatNumber(dto.getSeatNumber());
        seat.setSeatType(seatTypeRepo.findById(dto.getSeatTypeId())
                .orElseThrow(() -> new RuntimeException("SeatType not found")));
        seat.setSeatRow(seatRowRepo.findById(dto.getSeatRowId())
                .orElseThrow(() -> new RuntimeException("SeatRow not found")));
        return repo.save(seat);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<SeatDTO> getSeatsByHallId(Long hallId) {
        List<Seat> seats = repo.findBySeatRow_Hall_Id(hallId);
        return seats.stream()
                .map(seatMapper::toDto)
                .collect(Collectors.toList());
    }
}
