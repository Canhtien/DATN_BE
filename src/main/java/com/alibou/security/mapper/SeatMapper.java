package com.alibou.security.mapper;

import com.alibou.security.entity.Seat;
import com.alibou.security.entity.SeatRow;
import com.alibou.security.model.dto.SeatDTO;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {
    public SeatDTO toDto(Seat seat) {
        SeatDTO dto = new SeatDTO();
        dto.setId(seat.getId());
        dto.setSeatNumber(seat.getSeatNumber());
        dto.setSeatRowId(seat.getSeatRow().getId());
        dto.setHallId(seat.getSeatRow().getHall().getId());
        dto.setSeatStatus(String.valueOf(seat.getStatus()));
        dto.setSeatType(seat.getSeatType().getId());
        return dto;
    }

    public Seat toEntity(SeatDTO dto, SeatRow seatRow) {
        Seat seat = new Seat();
        seat.setId(dto.getId());
        seat.setSeatRow(seatRow);
        return seat;
    }
}
